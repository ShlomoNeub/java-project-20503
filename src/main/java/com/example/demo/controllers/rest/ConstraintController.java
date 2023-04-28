package com.example.demo.controllers.rest;


import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.Constraint;
import com.example.demo.db.repo.ConstraintRepo;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Controller that implements the Constraint REST API
 */
@RestController
@RequestMapping(path = "/constraints")
public class ConstraintController extends RestApiAbstract<Constraint, ConstraintRepo, Integer> {
    final Logger logger = LogManager.getLogger(ConstraintController.class);
    final ConstraintRepo repo;

    public ConstraintController(ConstraintRepo repo) {
        this.repo = repo;
    }

    @Override
    public ConstraintRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * Add constrain tot the user while making sure the user requesting 
     * can access it
     */
    @Auth
    @PostMapping("/user/")
    public Constraint addToUser(@RequestBody Constraint constraint, @AuthPayload AuthInfo info) {
        // here we make sure that the data is created for user
        constraint.setUserId(info.user().getId());
        List<Constraint> constraints =
                repo.findByDateRangeAndUser(info.user().getId(), constraint.getStartDate(), constraint.getEndDate());
        if (constraints.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already give constraint to this time");
        }
        return super.createNewEntity(constraint);
    }


    /**
     * Get all routes by time
     */
    @Auth
    @GetMapping("/user/range/{week}/{endWeek}")
    public List<Constraint> findByRangeWeek(@AuthPayload AuthInfo info, @PathVariable Integer week, @PathVariable Integer endWeek) {
        try {
            return repo.findByWeekRangeAndUser(info.user().getId(), week, endWeek);
        } catch (Exception e) {
            getLogger().error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all routes by time
     */
    @Auth
    @GetMapping("/user/range/{week}/{endWeek}/date")
    public List<Constraint> findByRangeDate(@AuthPayload AuthInfo info, @PathVariable Integer week, @PathVariable Integer endWeek) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            java.sql.Date start = new Date(calendar.getTime().getTime());
            calendar.set(Calendar.WEEK_OF_YEAR, endWeek);
            java.sql.Date end = new Date(calendar.getTime().getTime());
            return repo.findByDateRangeAndUser(info.user().getId(), start, end);
        } catch (Exception e) {
            getLogger().error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get all routes by time
     */
    @Auth
    @DeleteMapping("/user/{id}")
    public String deleteByIdAndUser(@AuthPayload AuthInfo info, @PathVariable Integer id) {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("deleted", repo.deleteByUserAndId(info.user().getId(), id) > 0);
            return object.toString();
        } catch (Exception e) {
            getLogger().error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}