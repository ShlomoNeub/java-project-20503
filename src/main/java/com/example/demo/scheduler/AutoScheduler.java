package com.example.demo.scheduler;

import com.example.demo.db.entities.*;
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
        this.id = id;
        this.autoScheduleMonitor = autoScheduleMonitor;
    }

    @Override
    public void run() {
        while (!isStop()) {
            try {
                ScheduleJob scheduleJob = autoScheduleMonitor.getJob(this);
                ScheduleJob result = doJob(scheduleJob);
                autoScheduleMonitor.finishJob(result);
            } catch (InterruptedException e) {
                logger.error(e.getCause());
            }
        }

    }


    private User nextUser(Queue<User> userQueue, Collection<Schedule> schedules) {
        while (!userQueue.isEmpty()) {
            User user = userQueue.poll();
            if (!isAlreadyScheduled(user, schedules)) {
                logger.debug("User %s selected for scheduling".formatted(user));
                return user;
            }
            logger.debug("User %s already scheduled".formatted(user));
        }
        return null;
    }

    private boolean isAlreadyScheduled(User user, Collection<Schedule> schedules) {
        return schedules.stream()
                .anyMatch(schedule -> schedule.getRequest().getUser().equals(user));
    }

    private Collection<Schedule> scheduleShift(Queue<User> userQueue, AvailableShifts shift) {
        Collection<Schedule> schedules = autoScheduleMonitor.getSchedulesByShift(shift.getId());
        Collection<Schedule> resultSchedules = new ArrayList<>();
        long workerCount = autoScheduleMonitor.getWorkersInShift(shift.getId());
        for (int i = 0; i < Math.max(0, shift.getEmployeeCount() - workerCount); i++) {
            User user = nextUser(userQueue,schedules);
            if(user == null) logger.warn("No user can schedule for %s".formatted(shift));
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


    private ScheduleJob doJob(ScheduleJob scheduleJob) {
        Collection<AvailableShifts> shifts = autoScheduleMonitor.getShiftsInRange(scheduleJob);
        Set<User> noScheduledUsers = new HashSet<>();
        for (AvailableShifts shift : shifts) {
            Queue<User> userQueue = new LinkedList<>();
            Set<User> freeUsers = new HashSet<>(autoScheduleMonitor.getUsers(shift));
            if(!noScheduledUsers.isEmpty()){
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

    synchronized public boolean isStop() {
        return stop;
    }

    synchronized public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "Worker{id=%d}".formatted(id);
    }
}
