package com.example.demo.controllers.rest;


import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.*;
import com.example.demo.db.repo.AvailableShiftsRepo;
import com.example.demo.db.repo.ProfileRepo;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.db.repo.ShiftRequestRepo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    final ProfileRepo profileRepo;
    final AvailableShiftsRepo availableShiftsRepo;
    final ShiftRequestRepo shiftRequestRepo;

    public ScheduleController(ScheduleRepo repo, ProfileRepo profileRepo, AvailableShiftsRepo availableShiftsRepo, ShiftRequestRepo shiftRequestRepo) {
        this.repo = repo;
        this.profileRepo = profileRepo;
        this.availableShiftsRepo = availableShiftsRepo;
        this.shiftRequestRepo = shiftRequestRepo;
    }

    /**
     * <b>Get /shifts_from_schedule</b>
     * <p>Add worker schedule</p>
     *
     * @param id of the request id
     * @throws ResponseStatusException when cannot execute correctly
     */
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


    /**
     * <b>Get /user/shifts/{week}/{day}</b>
     * <p>get user schedule by week and day</p>
     *
     * @param info of the caller user
     * @param week to search in
     * @param day  to search in
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth
    @GetMapping("/user/shifts/{week}/{day}")
    public String getUsersSchedules(@AuthPayload AuthInfo info, @PathVariable Integer day, @PathVariable Integer week) {

        return getUsersSchedules(info.user().getId(), day, week).toString();
    }

    /**
     * <b>Get /user/shifts/{week}/</b>
     * <p>get user schedule but by week</p>
     *
     * @param info of the caller user
     * @param week to search in
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth
    @GetMapping("/user/shifts/{week}/")
    public String getUsersSchedules(@AuthPayload AuthInfo info, @PathVariable Integer week) {
        JsonArray schedules = new JsonArray();
        for (int i = 0; i <= 7; i++) {
            schedules.addAll(getUsersSchedules(info.user().getId(), i, week));
        }
        return schedules.toString();
    }


    /**
     * <b>POST /addWorker</b>
     * <p>Add worker schedule</p>
     *
     * @param request json string with the request
     * @throws ResponseStatusException when cannot execute correctly
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addWorker", produces = "application/json")
    public String addWorker(@RequestBody String request) {
        int shiftId = 0;
        int profileId = 0;
        long numOfScheduledWorkers = 0;
        JsonObject jsonRequest;
        try {
            jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            shiftId = jsonRequest.get("sId").getAsInt();
            profileId = jsonRequest.get("pId").getAsInt();
        } catch (Exception e) {
            logger.error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = profileRepo.findById(profileId).get().getUsers().stream().toList().get(0);
        AvailableShifts shift = availableShiftsRepo.findById(shiftId).get();
        numOfScheduledWorkers = repo.countEmploieesInShift(shiftId);
        ShiftsRequests shiftsRequestsequest = new ShiftsRequests();
        shiftsRequestsequest.setUser(user);
        shiftsRequestsequest.setShift(shift);
        if (shift.getEmployeeCount() == numOfScheduledWorkers) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Shift Is Already Full!");
        }
        Schedule s = this.createSchedule(shiftsRequestsequest);


        JsonObject response = new JsonObject();
        response.addProperty("result", true);
        return response.toString();
    }


    /**
     * <b>DELETE /reset_shift/{sId}</b>
     * <p>reset all schedules for a shift</p>
     *
     * @param sId of the target {@code AvailableShit}
     * @throws ResponseStatusException when cannot execute correctly
     */
    @Auth
    @DeleteMapping("/reset_shift/{sId}")
    public String resetShift(@PathVariable Integer sId) {
        try {
            Collection<Schedule> scheduleCollection = repo.findByShiftId(sId);
            repo.deleteAll(scheduleCollection);
            return "Deleted";
        } catch (Exception e) {
            getLogger().error(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Helper function to get all schedules in JsonArray form
     *
     * @param userId who schedules we search for
     * @param day    who schedules we want
     * @param week   who schedules we want
     * @return array of all the schedules and with request field populated
     */
    private JsonArray getUsersSchedules(Integer userId, Integer day, Integer week) {
        List<Schedule> schedules = repo.findByUsersInRange(userId, week, day);
        JsonArray array = new JsonArray();
        for (Schedule schedule : schedules) {

            JsonObject object = JsonParser.parseString(privateGson.toJson(schedule)).getAsJsonObject();
            object.add("request", JsonParser.parseString(privateGson.toJson(schedule.getRequest())).getAsJsonObject());
            array.add(object);
        }
        return array;
    }

    /**
     * Helper that creates a schedule from a ShiftRequest
     *
     * @param request source to create schedule form
     * @return the created scheduled of null if not successful
     */
    private Schedule createSchedule(ShiftsRequests request) {
        try {
            Schedule schedule = new Schedule();
            request = shiftRequestRepo.save(request);
            schedule.setRequestId(request.getId());
            schedule.setWeekNumber(request.getShift().getWeekNumber());
            return repo.save(schedule);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public ScheduleRepo getAvailableShiftsRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
