package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.db.entities.AvailableShifts;
import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.User;
import com.example.demo.db.repo.AvailableShiftsRepo;
import com.example.demo.db.repo.ScheduleRepo;
import com.example.demo.db.repo.UserRepo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;
import java.util.*;

/**
 * Controller that implements the AvailableShifts REST API
 */
@RestController
@RequestMapping(path = "/available-shifts")
public class AvailableShiftsController extends RestApiAbstract<AvailableShifts, AvailableShiftsRepo, Integer> {
    final Logger logger = LogManager.getLogger(AvailableShiftsController.class);
    final AvailableShiftsRepo repo;
    final ScheduleRepo sRepo;
    final UserRepo userRepo;

    public AvailableShiftsController(AvailableShiftsRepo repo, ScheduleRepo sRepo, UserRepo userRepo) {
        this.repo = repo;
        this.sRepo = sRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/test")
    public String getAvailableShiftsStr(){
        Collection<AvailableShifts> availableShifts = getAll();
        JsonArray jsonOutput = new JsonArray();

        for(AvailableShifts as:availableShifts){
            JsonObject o = JsonParser.parseString(privateGson.toJson(as)).getAsJsonObject();
            o.addProperty("numOfScheduledWorkers",sRepo.findByRequestShiftId(as.getId()).size());
            jsonOutput.add(o);
        }
        return jsonOutput.toString();
    }


    @Override
    public AvailableShiftsRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private java.sql.Date getDate(int week,int day,int hour) {
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR, hour);
        return new java.sql.Date(calendar.getTime().getTime());
    }

    @Auth
    @GetMapping("/{id}/free-workers")
    public String getFreeWorkers(@PathVariable Integer id){
           Optional<AvailableShifts> shiftsOptional = repo.findById(id);
           if(shiftsOptional.isEmpty()){
               throw new ResponseStatusException(HttpStatus.NOT_FOUND);
           }
           java.sql.Date start, end;
           start = getDate(shiftsOptional.get().getWeekNumber(),shiftsOptional.get().getDayNumber(),shiftsOptional.get().getStartHour());
           end = new Date(start.getTime()+shiftsOptional.get().getDuration()*(60*1000));
           Collection<User> freeWorkersList = userRepo.findUsersFreeAt(start,end);

           JsonArray res = new JsonArray();

           for(User worker:freeWorkersList){
               JsonObject object = JsonParser.parseString(privateGson.toJson(worker.getProfile())).getAsJsonObject();
               res.add(object);
           }
            return res.toString();
    }


    /**
     * Find all available shifts for a specific week number
     * @param weekNumber the week number to search for
     * @return a Collection of available shifts for the given week number
     */
    @GetMapping("/week/{weekNumber}")
    public Collection<AvailableShifts> findByWeekNumber(@PathVariable Integer weekNumber) {
        return repo.findByWeekNumber(weekNumber);
    }

    /**
     * Find shifts between days in a given week
     * @param weekNumber the week number to search for
     * @param startDay the start day number to search for
     * @param endDay the end day number to search for
     * @return a Collection of available shifts between the given start and end days in the specified week
     */
    @GetMapping("/week/{weekNumber}/days/{startDay}/{endDay}")
    public Collection<AvailableShifts> findShiftsBetweenDaysInWeek(@PathVariable Integer weekNumber,
                                                             @PathVariable Integer startDay,
                                                             @PathVariable Integer endDay) {
        return repo.findShiftsBetweenDaysInWeek(weekNumber, startDay, endDay);
    }

    /**
     * Find the shifts with the longest duration for a given day and week
     * @param weekNumber the week number to search for
     * @param dayNumber the day number to search for
     * @return a Collection of available shifts with the longest duration for the given day and week
     */
    @GetMapping("/week/{weekNumber}/day/{dayNumber}/longest-duration")
    public Collection<AvailableShifts> findShiftWithLongestDuration(@PathVariable Integer weekNumber,
                                                              @PathVariable Integer dayNumber) {
        return repo.findByWeekNumberAndDayNumberOrderByDurationDesc(weekNumber, dayNumber);
    }

    /**
     * <b>Post /addShift</b>
     * <p>add shift route for the system</p>
     *
     * @param request stringified json with flattened profile and user
     * @return Json stringified with username, nad if it was created.
     * @throws ResponseStatusException when cannot execute correctly
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addShift", produces = "application/json")
    public String addShift(@RequestBody String request) {
        JsonObject jsonRequest;
        int weekNumber;
        int dayNumber;
        int startHour;
        int duration;
        int employeeCount;
        int year;
        AvailableShifts availableShifts = null;
        try {
            jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            availableShifts = new AvailableShifts();
            weekNumber = jsonRequest.get("weekNumber").getAsInt();
            dayNumber = jsonRequest.get("dayNumber").getAsInt();
            startHour = jsonRequest.get("startHour").getAsInt();
            duration = jsonRequest.get("duration").getAsInt();
            employeeCount = jsonRequest.get("numOfRequiredWorkers").getAsInt();
            year = jsonRequest.get("year").getAsInt();
            availableShifts.setDayNumber(dayNumber);
            availableShifts.setWeekNumber(weekNumber);
            availableShifts.setStartHour(startHour);
            availableShifts.setDuration(duration);
            availableShifts.setEmployeeCount(employeeCount);
            availableShifts.setYear(year);

            repo.save(availableShifts);
        } catch (Exception e) {
            logger.error(e);
            if (availableShifts != null && availableShifts.getId() != null) repo.delete(availableShifts);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        JsonObject response = new JsonObject();
        response.addProperty("Shift", availableShifts.toString());
        response.addProperty("created", true);

        return response.toString();
    }

}