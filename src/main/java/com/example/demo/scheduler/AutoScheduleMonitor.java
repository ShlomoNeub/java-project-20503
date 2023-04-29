package com.example.demo.scheduler;

import com.example.demo.db.entities.*;
import com.example.demo.db.repo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This call implement monitor design pattern for thread management.
 * It is a Component bean so it's initialized once in every spring boot run
 */
@Service
public class AutoScheduleMonitor {

    final Logger logger = LogManager.getLogger(AutoScheduleMonitor.class);
    final ScheduleJobRepo scheduleJobRepo;
    final AvailableShiftsRepo availableShiftsRepo;
    final ScheduleRepo scheduleRepo;
    final UserRepo userRepo;
    private final ShiftRequestRepo shiftRequestRepo;
    private final ReentrantLock repoAccessLock = new ReentrantLock();
    ArrayList<AutoScheduler> workers = new ArrayList<>();
    Queue<ScheduleJob> scheduleJobs = new LinkedList<>();


    public AutoScheduleMonitor(ScheduleJobRepo repo, AvailableShiftsRepo availableShiftsRepo, ScheduleRepo scheduleRepo, UserRepo userRepo,
                               ShiftRequestRepo shiftRequestRepo) {
        // init all repos
        this.scheduleJobRepo = repo;
        this.availableShiftsRepo = availableShiftsRepo;
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
        this.shiftRequestRepo = shiftRequestRepo;


        // count how many vCPU the system has and use half of them to use for workers
        int processor = Math.min(1, Runtime.getRuntime().availableProcessors() / 2);
//        int processor = 0;
        // create new workers
        for (int i = 0; i < processor; i++) {
            AutoScheduler worker = new AutoScheduler(i, this);
            workers.add(worker);
        }
        // initiate all the workers
        workers.forEach(Thread::start);
        logger.info("Manger is created %d workers".formatted(workers.size()));
        // get undone jobs and run them
        repo.findByNotDone().forEach(this::addJob);
    }

    /**
     * Add job the run and alert any waiting worker
     *
     * @param scheduleJob to be saved and run when possible
     */
    synchronized public void addJob(ScheduleJob scheduleJob) {

        scheduleJobRepo.save(scheduleJob);
        scheduleJobs.add(scheduleJob);
        notify();
    }

    /**
     * get a job from the queue
     *
     * @param worker that requests the job
     * @return a job for the worker to consume
     * @throws InterruptedException when unable to wait
     */
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

    public Collection<AvailableShifts> getShiftsInRange(ScheduleJob scheduleJob) {
        repoAccessLock.lock();
        try {
            int[] dateValues = getDates(scheduleJob);
            int startWeek = dateValues[0];
            int endWeek = dateValues[1];
            int startDay = dateValues[2];
            int endDay = dateValues[3];
            return availableShiftsRepo.findShiftsInRange(startWeek, endWeek, startDay, endDay);
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        } finally {
            repoAccessLock.unlock();
        }
    }


    public Collection<User> getUsers(AvailableShifts shifts) {
        repoAccessLock.lock();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR, shifts.getWeekNumber());
            calendar.set(Calendar.DAY_OF_WEEK, shifts.getDayNumber());

            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            java.sql.Date endDate = new java.sql.Date((startDate.getTime() + shifts.getDuration() * 60 * 1000));
            return userRepo.findUsersFreeConstraintsAt(startDate, endDate);
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        } finally {
            repoAccessLock.unlock();
        }
    }


    public List<ShiftsRequests> getRequests(Integer shiftId) {
        repoAccessLock.lock();
        try {
            return shiftRequestRepo.getShiftsRequestsByShiftIdFree(shiftId).stream().filter(s -> s.getSchedule() == null).toList();
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        } finally {
            repoAccessLock.unlock();
        }


    }

    public long getWorkersInShift(Integer shiftId) {
        repoAccessLock.lock();
        try {
            return scheduleRepo.countEmploieesInShift(shiftId);
        } catch (Exception e) {
            logger.error(e);
            return Long.MAX_VALUE;
        } finally {
            repoAccessLock.unlock();
        }
    }

    public Collection<Schedule> getSchedulesByShift(int id) {
        repoAccessLock.lock();
        try {
            return scheduleRepo.findByShiftId(id);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            repoAccessLock.unlock();
        }
    }


    public Schedule createSchedule(ShiftsRequests request) {
        repoAccessLock.lock();
        try {
            AvailableShifts shifts = availableShiftsRepo.findById(request.getShiftId()).orElse(null);
            if (shifts == null) return null;
            if (shifts.getEmployeeCount() <= scheduleRepo.countEmploieesInShift(shifts.getId())) return null;
            Schedule schedule = new Schedule();
            request = shiftRequestRepo.save(request);
            schedule.setRequestId(request.getId());
            schedule.setWeekNumber(request.getShift().getWeekNumber());
            return scheduleRepo.save(schedule);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            repoAccessLock.unlock();
        }
    }

}
