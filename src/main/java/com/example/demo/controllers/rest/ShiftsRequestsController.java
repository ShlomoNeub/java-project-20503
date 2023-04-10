package com.example.demo.controllers.rest;

import com.example.demo.db.entities.ShiftsRequests;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.db.repo.ShiftRequestRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that implements the Schedule REST API
 * @return all the shifts requests from table
 */
@RestController
@RequestMapping(path = "/shiftsrequests")
public class ShiftsRequestsController extends RestApiAbstract<ShiftsRequests, ShiftRequestRepo,Integer>{

    final Logger logger = LogManager.getLogger(ShiftsRequestsController.class);
    final ShiftRequestRepo repo;

    public ShiftsRequestsController(ShiftRequestRepo repo) {
        this.repo = repo;
    }
    @Override
    public ShiftRequestRepo getRepo() {
        return repo;
    }
    @Override
    public Logger getLogger() {
        return logger;
    }
}
