package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.ScheduleJob;
import com.example.demo.db.repo.ScheduleJobRepo;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.scheduler.AutoScheduleMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(path = "/jobs")
public class JobController extends RestApiAbstract<ScheduleJob, ScheduleJobRepo, Integer> {

    final Logger logger = LogManager.getLogger(JobController.class);
    final ScheduleJobRepo scheduleJobRepo;
    final ScheduleRepo scheduleRepo;
    final AutoScheduleMonitor autoScheduleMonitor;

    public JobController(ScheduleJobRepo scheduleJobRepo, AutoScheduleMonitor autoScheduleMonitor, ScheduleRepo scheduleRepo) {
        this.scheduleJobRepo = scheduleJobRepo;
        this.autoScheduleMonitor = autoScheduleMonitor;
        this.scheduleRepo = scheduleRepo;
    }


    @Override
    public ScheduleJob createNewEntity(ScheduleJob entity) {
        ScheduleJob job = super.createNewEntity(entity);
        autoScheduleMonitor.addJob(job);
        return job;
    }


    @GetMapping("/user/")
    @Auth
    public Collection<ScheduleJob> getJobByUid(@AuthPayload AuthInfo info) {
        return scheduleJobRepo.findByUserId(info.user().getId());
    }

    @Override
    public ScheduleJob updateById(Integer id, ScheduleJob entity) {
        // Job can not be updated they can only be deleted
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ScheduleJobRepo getRepo() {
        return scheduleJobRepo;
    }
}
