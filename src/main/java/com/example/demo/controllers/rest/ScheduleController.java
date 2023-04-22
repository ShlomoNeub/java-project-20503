package com.example.demo.controllers.rest;


import com.example.demo.config.annotation.Auth;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.db.repo.ShiftRequestRepo;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
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
    final ProfileRepo profileRepo;
    final AvailableShiftsRepo availableShiftsRepo;
    final ShiftRequestRepo shiftRequestRepo;



    public ScheduleController(ScheduleRepo repo, ProfileRepo profileRepo, AvailableShiftsRepo availableShiftsRepo, ShiftRequestRepo shiftRequestRepo) {
        this.repo = repo;
        this.profileRepo = profileRepo;
        this.availableShiftsRepo = availableShiftsRepo;

        this.shiftRequestRepo = shiftRequestRepo;
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

    @RequestMapping(method = RequestMethod.POST, path = "/addWorker", produces = "application/json")
    public String addWorker(@RequestBody String request) {
        int shiftId = 0;
        int profileId = 0;
        JsonObject jsonRequest;
        try {
            jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            shiftId = jsonRequest.get("sId").getAsInt();
            profileId = jsonRequest.get("pId").getAsInt();
            User user = profileRepo.findById(profileId).get().getUsers().stream().toList().get(0);
            AvailableShifts shift = availableShiftsRepo.findById(shiftId).get();
            ShiftsRequests shiftsRequestsequest = new ShiftsRequests();
            shiftsRequestsequest.setUser(user);
            shiftsRequestsequest.setShift(shift);
            shiftsRequestsequest.setTimestamp(new Timestamp(System.currentTimeMillis()));
            Schedule s = this.createSchedule(shiftsRequestsequest);
        } catch (Exception e) {
            logger.error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        JsonObject response = new JsonObject();
        response.addProperty("result", true);
        return response.toString();
    }

    public Schedule createSchedule(ShiftsRequests request) {
        try {
            Schedule schedule = new Schedule();
            request = shiftRequestRepo.save(request);
            schedule.setRequestId(request.getId());
            schedule.setRequest(request);
            schedule.setWeekNumber(request.getShift().getWeekNumber());
            return repo.save(schedule);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

}
