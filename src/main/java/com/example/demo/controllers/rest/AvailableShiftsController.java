package com.example.demo.controllers.rest;

import com.example.demo.db.entities.AvailableShifts;
import com.example.demo.db.repo.AvailableShiftsRepo;
import com.example.demo.db.repo.ScheduleRepo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collection;

/**
 * Controller that implements the AvailableShifts REST API
 */
@RestController
@RequestMapping(path = "/available-shifts")
public class AvailableShiftsController extends RestApiAbstract<AvailableShifts, AvailableShiftsRepo, Integer> {
    final Logger logger = LogManager.getLogger(AvailableShiftsController.class);
    final AvailableShiftsRepo repo;
    final ScheduleRepo sRepo;

    public AvailableShiftsController(AvailableShiftsRepo repo, ScheduleRepo sRepo) {
        this.repo = repo;
        this.sRepo = sRepo;
    }

    @GetMapping("/test")
    public String getAvailableShiftsStr(){
        Collection<AvailableShifts> availableShifts = getAll();
        JsonArray jsonOutput = new JsonArray();

        for(AvailableShifts as:availableShifts){
            JsonObject o = JsonParser.parseString(new Gson().toJson(as)).getAsJsonObject();
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
}