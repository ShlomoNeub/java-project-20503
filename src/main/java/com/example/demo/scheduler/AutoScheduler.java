package com.example.demo.scheduler;

import com.example.demo.db.entities.AvailableShifts;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.entities.ShiftsRequests;
import com.example.demo.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class Worker extends Thread {
    final Logger logger = LogManager.getLogger(Worker.class);
    private final int id;

    private boolean stop = false;
    private final Manager manager;

    public Worker(int id, Manager manager) {
        this.id = id;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (!isStop()) {
            try {
                Job job = manager.getJob(this);
                Job result = doJob(job);
                manager.finishJob(result);
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
        Collection<Schedule> schedules = manager.getSchedulesByShift(shift.getId());
        Collection<Schedule> resultSchedules = new ArrayList<>();
        long workerCount = manager.getWorkersInShift(shift.getId());
        for (int i = 0; i < Math.max(0, shift.getEmployeeCount() - workerCount); i++) {
            User user = nextUser(userQueue,schedules);
            if(user == null) logger.warn("No user can schedule for %s".formatted(shift));
            ShiftsRequests request = new ShiftsRequests();
            request.setUser(user);
            request.setShift(shift);
            request.setTimestamp(new Timestamp(System.currentTimeMillis()));
            Schedule s = manager.createSchedule(request);
            schedules.add(s);
            resultSchedules.add(s);
        }
        return resultSchedules;
    }


    private Job doJob(Job job) {
        Collection<AvailableShifts> shifts = manager.getShiftsInRange(job);
        Set<User> noScheduledUsers = new HashSet<>();
        for (AvailableShifts shift : shifts) {
            Queue<User> userQueue = new LinkedList<>();
            Set<User> freeUsers = new HashSet<>(manager.getUsers(shift));
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
        return job;
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
