package com.example.demo.controllers.rest;

import com.example.demo.db.entities.AvailableShifts;
import com.example.demo.db.repo.AvailableShiftsRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller that implements the AvailableShifts REST API
 */
@RestController
@RequestMapping(path = "/available-shifts")
public class AvailableShiftsController extends RestApiAbstract<AvailableShifts, AvailableShiftsRepo, Integer> {
    final Logger logger = LogManager.getLogger(AvailableShiftsController.class);
    final AvailableShiftsRepo repo;

    public AvailableShiftsController(AvailableShiftsRepo repo) {
        this.repo = repo;
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
     * @return a list of available shifts for the given week number
     */
    @GetMapping("/week/{weekNumber}")
    public List<AvailableShifts> findByWeekNumber(@PathVariable Integer weekNumber) {
        return repo.findByWeekNumber(weekNumber);
    }

    /**
     * Find shifts between days in a given week
     * @param weekNumber the week number to search for
     * @param startDay the start day number to search for
     * @param endDay the end day number to search for
     * @return a list of available shifts between the given start and end days in the specified week
     */
    @GetMapping("/week/{weekNumber}/days/{startDay}/{endDay}")
    public List<AvailableShifts> findShiftsBetweenDaysInWeek(@PathVariable Integer weekNumber,
                                                             @PathVariable Integer startDay,
                                                             @PathVariable Integer endDay) {
        return repo.findShiftsBetweenDaysInWeek(weekNumber, startDay, endDay);
    }

    /**
     * Find the shifts with the longest duration for a given day and week
     * @param weekNumber the week number to search for
     * @param dayNumber the day number to search for
     * @return a list of available shifts with the longest duration for the given day and week
     */
    @GetMapping("/week/{weekNumber}/day/{dayNumber}/longest-duration")
    public List<AvailableShifts> findShiftWithLongestDuration(@PathVariable Integer weekNumber,
                                                              @PathVariable Integer dayNumber) {
        return repo.findShiftWithLongestDuration(weekNumber, dayNumber);
    }
}