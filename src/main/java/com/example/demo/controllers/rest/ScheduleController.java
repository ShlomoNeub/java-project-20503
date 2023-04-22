package com.example.demo.controllers.rest;


import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.repo.ScheduleRepo;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * Controller that implements the Schedule REST API
 *
 */
@RestController
@RequestMapping(path = "/schedules")
public class ScheduleController extends RestApiAbstract<Schedule, ScheduleRepo, Integer> {

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


    @GetMapping("/job/{id}")
    @Auth
    public Collection<Schedule> getJobByUid(@AuthPayload AuthInfo info, @PathVariable Integer id) {
        return repo.getScheduleJobsByJobId(id);
    }

    @Auth
    @GetMapping("/shifts_from_schedule")
    public String getAvailableShiftsStr(@RequestParam Integer id) {
        Collection<Schedule> schedules = repo.findByRequestShiftId(id);
        JsonArray res = new JsonArray();

        for (Schedule s : schedules) {
            Profile p = s.getRequest().getUser().getProfile();
            JsonObject object = JsonParser.parseString(privateGson.toJson(p)).getAsJsonObject();
            res.add(object);
        }
        return res.toString();
    }

}
