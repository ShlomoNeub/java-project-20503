package com.example.demo.controllers.rest;

import com.example.demo.db.entities.ShiftsRequests;
import com.example.demo.db.repo.ShiftRequestRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller that implements the Schedule REST API
 */
@RestController
@RequestMapping(path = "/shiftsrequests")
public class ShiftsRequestsController extends RestApiAbstract<ShiftsRequests, ShiftRequestRepo, Integer> {

    final Logger logger = LogManager.getLogger(ShiftsRequestsController.class);
    final ShiftRequestRepo repo;


    @Override
    @Auth
    public ShiftsRequests createNewEntity(ShiftsRequests entity) {
        ShiftsRequests requests = repo.findByUidAndShiftId(entity.getUid(), entity.getShiftId()).orElse(null);
        if (requests != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already submitted");
        } else {
            return super.createNewEntity(entity);
        }
    }

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
