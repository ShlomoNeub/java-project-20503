package com.example.demo.scheduler;

import com.example.demo.db.entities.*;
import jakarta.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class AutoScheduler extends Thread {
    final Logger logger = LogManager.getLogger(AutoScheduler.class);
    private final int id;

    private boolean stop = false;
    private final AutoScheduleMonitor autoScheduleMonitor;

    public AutoScheduler(int id, AutoScheduleMonitor autoScheduleMonitor) {
        super("autoScheduler %s".formatted(id));
        this.id = id;
        this.autoScheduleMonitor = autoScheduleMonitor;
    }

    @Override
    public void run() {
        while (!isStop()) {
            try {
                // get next job from monitor
                ScheduleJob scheduleJob = autoScheduleMonitor.getJob(this);
                // run scheduler and save the result
                ScheduleJob result = doJob(scheduleJob);
                // notify the monitor the job is finished
                autoScheduleMonitor.finishJob(result);
            } catch (InterruptedException e) {
                logger.error(e.getCause());
            }
        }

    }

    synchronized public boolean isStop() {
        return stop;
    }

    synchronized public void setStop(boolean stop) {
        this.stop = stop;
    }


    /**
     * Create all the schedules needed to fill a shift
     *
     * @param userQueue of the available users
     * @param shift     to be filled
     * @return all the schedules created by this function
     */
    private Collection<Schedule> scheduleShift(Queue<User> userQueue, AvailableShifts shift) {
        Collection<Schedule> schedules = autoScheduleMonitor.getSchedulesByShift(shift.getId());
        Set<User> scheduledUsers = schedules.stream().map(s->s.getRequest().getUser()).collect(Collectors.toSet());
        Collection<Schedule> resultSchedules = new ArrayList<>();
        long workerCount = autoScheduleMonitor.getWorkersInShift(shift.getId());
        for (int i = 0; i < Math.max(0, shift.getEmployeeCount() - workerCount); i++) {
            User user = nextUser(userQueue, scheduledUsers);
            if (user == null) logger.warn("No user can schedule for %s".formatted(shift));
            ShiftsRequests request = new ShiftsRequests();
            request.setUser(user);
            request.setShift(shift);
            request.setTimestamp(new Timestamp(System.currentTimeMillis()));
            Schedule s = autoScheduleMonitor.createSchedule(request);
            schedules.add(s);
            resultSchedules.add(s);
        }
        return resultSchedules;
    }

    /**
     * This function parse the job information and runs on the shifts requested
     *
     * @param scheduleJob the job that needed to be done
     * @return the job that done
     */
    private ScheduleJob doJob(ScheduleJob scheduleJob) {
        Collection<AvailableShifts> shifts = autoScheduleMonitor.getShiftsInRange(scheduleJob);
        Set<User> noScheduledUsers = new HashSet<>();
        for (AvailableShifts shift : shifts) {
            Queue<User> userQueue = new LinkedList<>();
            Set<User> freeUsers = new HashSet<>(autoScheduleMonitor.getUsers(shift));
            if (!noScheduledUsers.isEmpty()) {
                userQueue.addAll(noScheduledUsers.stream().filter(freeUsers::contains).collect(Collectors.toSet()));
                userQueue.forEach(freeUsers::remove);
            }
            userQueue.addAll(freeUsers);

            Collection<Schedule> schedule = scheduleShift(userQueue, shift);
            schedule.forEach(s -> logger.debug("new scheduled added %s".formatted(s)));
            noScheduledUsers.clear();
            noScheduledUsers.addAll(userQueue.stream().toList());
        }
        return scheduleJob;
    }


    /**
     * Helper function that receive available user and get the next user that was not already in schedules
     *
     * @param userQueue with the user
     * @param schedulesUsers that already were scheduled
     * @return the next available user or null
     */
    @Nullable
    private User nextUser(Queue<User> userQueue,  Set<User> schedulesUsers) {
        return userQueue.stream()
                .filter(user -> !isAlreadyScheduled(user, schedulesUsers))
                .findFirst()
                .orElse(null);
    }

    /**
     * Helper function that checks if a given user is in the given usersSet
     *
     * @param user      to search for
     * @param schedulesUsers to search in
     * @return if the user is in any of the scheduled schedules
     */
    private boolean isAlreadyScheduled(User user, Set<User> schedulesUsers) {
        return schedulesUsers.contains(user);
    }

    @Override
    public String toString() {
        return "AutoScheduler {id=%d}".formatted(id);
    }
}
