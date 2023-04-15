package com.example.demo.db.repo;

import com.example.demo.db.entities.AvailableShifts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AvailableShiftsRepo extends CrudRepository<AvailableShifts,Integer> {


    // Find all available shifts for a specific week number
    Collection<AvailableShifts> findByWeekNumber(Integer weekNumber);

    // Find all available shifts for a specific day number in a given week
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber =?2 ORDER BY a.startHour DESC")
    Collection<AvailableShifts> findByWeekNumberAndDayNumber(Integer weekNumber, Integer dayNumber);
    // Find all available shifts for a specific day number in a given week
    Collection<AvailableShifts> findByWeekNumberAndDayNumberOrderByDurationDesc(Integer weekNumber, Integer dayNumber);

    // find shifts between days in a given week
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber >=?2 AND a.dayNumber <=?3")
    Collection<AvailableShifts> findShiftsBetweenDaysInWeek(Integer weekNumber, Integer startDay, Integer endDay);

    Collection<AvailableShifts> findByEmployeeCountGreaterThanEqual(Integer employeeCount);


    Collection<AvailableShifts> getAvailableShiftsByYear(Integer year);




}
