package com.example.demo.controllers.rest;


import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.repo.ScheduleRepo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


/**
 * Controller that implements the Schedule REST API
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


    @Auth
    @GetMapping("/user/shifts/{week}/{day}")
    public String getUsersSchedules(@AuthPayload AuthInfo info, @PathVariable Integer day, @PathVariable Integer week) {

        return getUsersSchedules(info.user().getId(), day,week).toString();
    }

    @Auth
    @GetMapping("/user/shifts/{week}/")
    public String getUsersSchedules(@AuthPayload AuthInfo info, @PathVariable Integer week) {
        JsonArray schedules = new JsonArray();
        for (int i = 0; i < 6; i++) {
            schedules.addAll(getUsersSchedules(info.user().getId(), i, week));
        }
        return schedules.toString();
    }


    private  JsonArray getUsersSchedules(Integer userId,Integer day, Integer week){
        List<Schedule> schedules = repo.findByUsersInRange(userId, week, day);
        JsonArray array = new JsonArray();
        for (Schedule schedule : schedules) {
            array.add(JsonParser.parseString(privateGson.toJson(schedule)).getAsJsonObject());
        }
        return array;
    }

}
