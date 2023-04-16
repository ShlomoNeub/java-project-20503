package com.example.demo.scheduler;

import com.example.demo.db.entities.*;
import com.example.demo.db.repo.*;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class AutoScheduleMonitor {

    final Logger logger = LogManager.getLogger(AutoScheduleMonitor.class);

    ArrayList<AutoScheduler> workers = new ArrayList<>();
    Queue<ScheduleJob> scheduleJobs = new LinkedList<>();

    final ScheduleJobRepo scheduleJobRepo;
    final AvailableShiftsRepo availableShiftsRepo;
    final ScheduleRepo scheduleRepo;
    final UserRepo userRepo;
    private final ShiftRequestRepo shiftRequestRepo;

    public AutoScheduleMonitor(ScheduleJobRepo repo, AvailableShiftsRepo availableShiftsRepo, ScheduleRepo scheduleRepo, UserRepo userRepo,
                               ShiftRequestRepo shiftRequestRepo) {
        this.scheduleJobRepo = repo;
        this.availableShiftsRepo = availableShiftsRepo;
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
        scheduleJobs.addAll(repo.findByNotDone());
        int processor = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
        for (int i = 0; i < processor; i++) {
            AutoScheduler worker = new AutoScheduler(i, this);
            workers.add(worker);
        }
        workers.forEach(Thread::start);
        logger.info("Manger is created %d workers".formatted(workers.size()));
        this.shiftRequestRepo = shiftRequestRepo;
    }

    synchronized public void addJob(ScheduleJob scheduleJob) {
        scheduleJobRepo.save(scheduleJob);
        scheduleJobs.add(scheduleJob);
        notify();
    }

    synchronized public ScheduleJob getJob(AutoScheduler worker) throws InterruptedException {
        while (scheduleJobs.isEmpty()) {
            logger.info("%s start waiting".formatted(worker));
            Date date = Date.from(Instant.now());
            wait();
            long waitedFor = Date.from(Instant.now()).getTime() - date.getTime();
            logger.info("%s finished waiting (waited for %s ms)".formatted(worker, waitedFor));
        }
        return scheduleJobs.poll();
    }


    synchronized public void finishJob(ScheduleJob result) {
        result.setDone(true);
        scheduleJobRepo.save(result);
    }


    private int[] getDates(ScheduleJob scheduleJob) {
        Date startDate = new java.util.Date(scheduleJob.getStartDate().getTime());
        Date endDate = new java.util.Date(scheduleJob.getEndDate().getTime());
        Calendar calendar = java.util.Calendar.getInstance();

        calendar.setTime(startDate);
        int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(endDate);
        int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int endDay = calendar.get(Calendar.DAY_OF_WEEK);
        return new int[]{startWeek, endWeek, startDay, endDay};
    }

    synchronized public Collection<AvailableShifts> getShiftsInRange(ScheduleJob scheduleJob) {
        int[] dateValues = getDates(scheduleJob);
        int startWeek = dateValues[0];
        int endWeek = dateValues[1];
        int startDay = dateValues[2];
        int endDay = dateValues[3];

        return availableShiftsRepo.findShiftsInRange(startWeek, endWeek, startDay, endDay);
    }

    synchronized public Collection<Integer> getShiftsIdInRange(ScheduleJob scheduleJob) {
        int[] dateValues = getDates(scheduleJob);
        int startWeek = dateValues[0];
        int startDay = dateValues[1];
        int endWeek = dateValues[2];
        int endDay = dateValues[3];

        return availableShiftsRepo.findShiftsIdInRange(startWeek, endWeek, startDay, endDay);
    }

    synchronized public Collection<User> getUsers(ScheduleJob scheduleJob) {
        return userRepo.findUsersFreeAt(scheduleJob.getStartDate(), scheduleJob.getEndDate());
    }

    synchronized public Collection<User> getUsers(AvailableShifts shifts) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, shifts.getWeekNumber());
        calendar.set(Calendar.DAY_OF_WEEK, shifts.getDayNumber());

        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
        java.sql.Date endDate = new java.sql.Date((startDate.getTime() + shifts.getDuration() * 60 * 1000));
        return userRepo.findUsersFreeAt(startDate, endDate);
    }

    synchronized public Collection<Schedule> getSchedules(ScheduleJob j) {
        return scheduleRepo.findByRequest_Shift_IdIn(getShiftsIdInRange(j));
    }

    synchronized public long getWorkersInShift(Integer shiftId) {
        return scheduleRepo.countEmploieesInShift(shiftId);
    }

    synchronized public Collection<Schedule> getSchedulesByShift(int id) {
        return scheduleRepo.findByShiftId(id);
    }

    synchronized public Schedule createSchedule(ShiftsRequests request) {
        Schedule schedule = new Schedule();
        request = shiftRequestRepo.save(request);
        schedule.setRequestId(request.getId());
        schedule.setRequest(request);
        schedule.setWeekNumber(request.getShift().getWeekNumber());
        return scheduleRepo.save(schedule);
    }

}
