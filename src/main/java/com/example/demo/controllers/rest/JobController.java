package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.db.entities.ScheduleJob;
import com.example.demo.db.repo.ScheduleJobRepo;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.scheduler.AutoScheduleMonitor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;

@RestController
@RequestMapping(path = "/jobs")
public class JobController extends RestApiAbstract<ScheduleJob, ScheduleJobRepo,Integer>{

    final Logger logger = LogManager.getLogger(JobController.class);
    final ScheduleJobRepo scheduleJobRepo;

    final AutoScheduleMonitor autoScheduleMonitor;

    public JobController(ScheduleJobRepo scheduleJobRepo, AutoScheduleMonitor autoScheduleMonitor) {
        this.scheduleJobRepo = scheduleJobRepo;
        this.autoScheduleMonitor = autoScheduleMonitor;
    }


    @Override
    @Auth
    public ScheduleJob createNewEntity(ScheduleJob entity) {
        ScheduleJob job = super.createNewEntity(entity);
        autoScheduleMonitor.addJob(job);
        return job;
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
