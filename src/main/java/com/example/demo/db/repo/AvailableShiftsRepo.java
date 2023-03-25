package com.example.demo.db.repo;

import com.example.demo.db.entities.AvailableShifts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvailableShiftsRepo extends CrudRepository<AvailableShifts,Integer> {

    // Find all available shifts for a specific week number
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1")
    List<AvailableShifts> findByWeekNumber(Integer weekNumber);

    // Find all available shifts for a specific day number in a given week
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber =?2")
    List<AvailableShifts> findByWeekNumberAndDayNumber(Integer weekNumber, Integer dayNumber);

    // find shifts between days in a given week
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber >=?2 AND a.dayNumber <=?3")
    List<AvailableShifts> findShiftsBetweenDaysInWeek(Integer weekNumber, Integer startDay, Integer endDay);


    // Find all available shifts with a specific number of employees or more
    @Query ("SELECT a FROM AvailableShifts a WHERE a.employeeCount >=?1")
    List<AvailableShifts> findEmployeeCountGreaterThanEqual(Integer employeeCount);

    // Find all available shifts with a specific number of managers or more
    @Query ("SELECT a FROM AvailableShifts a WHERE a.mangerCount >=?1")
    List<AvailableShifts> findMangerCountGreaterThanEqual(Integer managerCount);

    //find the shifts with the longest duration  given a day and a week
    @Query ("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber =?2 ORDER BY a.duration DESC")
    List<AvailableShifts> findShiftWithLongestDuration(Integer weekNumber, Integer dayNumber);




    //


}
