package com.example.demo.controllers.rest;


import com.example.demo.db.entities.Schedule;
import com.example.demo.db.repo.ScheduleRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller that implements the Schedule REST API
 */
@RestController
@RequestMapping(path = "/schedules")
public class ScheduleController extends RestApiAbstract<Schedule, ScheduleRepo,Integer>{

    final Logger logger = LogManager.getLogger(ScheduleController.class);
    final ScheduleRepo repo;

    public ScheduleController(ScheduleRepo repo) {
        this.repo = repo;
    }

    @Override
    public ScheduleRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
