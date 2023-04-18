package com.example.demo.db.repo;

import com.example.demo.db.entities.AvailableShifts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface AvailableShiftsRepo extends CrudRepository<AvailableShifts, Integer> {


    // Find all available shifts for a specific week number
    Collection<AvailableShifts> findByWeekNumber(Integer weekNumber);

    // Find all available shifts for a specific day number in a given week
    @Query("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber =?2 ORDER BY a.startHour DESC")
    Collection<AvailableShifts> findByWeekNumberAndDayNumber(Integer weekNumber, Integer dayNumber);

    // Find all available shifts for a specific day number in a given week
    Collection<AvailableShifts> findByWeekNumberAndDayNumberOrderByDurationDesc(Integer weekNumber, Integer dayNumber);

    // find shifts between days in a given week
    @Query("SELECT a FROM AvailableShifts a WHERE a.weekNumber =?1 AND a.dayNumber >=?2 AND a.dayNumber <=?3")
    Collection<AvailableShifts> findShiftsBetweenDaysInWeek(Integer weekNumber, Integer startDay, Integer endDay);

    /**
     * Finds shift within range<br/>
     * {@code weekNumberStart <= s.weekNumber && s.weekNumber <= weekNumberEnd && dayNumberStart <=s.dayNumber && s.dayNumber <= dayNumberEnd}
     *
     * @param weekNumberStart start of week range
     * @param weekNumberEnd   end of week range
     * @param dayNumberStart  start of day range
     * @param dayNumberEnd    end of day range
     * @return all shifts that in range
     */
    @Query("select a from AvailableShifts a where a.weekNumber between ?1 and ?2 and a.dayNumber between ?3 and ?4")
    Collection<AvailableShifts> findShiftsInRange(@NonNull Integer weekNumberStart,
                                                  @NonNull Integer weekNumberEnd,
                                                  @NonNull Integer dayNumberStart,
                                                  @NonNull Integer dayNumberEnd);

    /**
     * same as {@link #findShiftsIdInRange}
     *
     * @param weekNumberStart start of week range
     * @param weekNumberEnd   end of week range
     * @param dayNumberStart  start of day range
     * @param dayNumberEnd    end of day range
     * @return same as {@link #findShiftsIdInRange} but the projection is only the Ids
     */
    @Query("select a.id from AvailableShifts a where a.weekNumber >= ?1 and a.weekNumber <= ?2 and  a.dayNumber >= ?3 and a.dayNumber<= ?4")
    Collection<Integer> findShiftsIdInRange(@NonNull Integer weekNumberStart,
                                            @NonNull Integer weekNumberEnd,
                                            @NonNull Integer dayNumberStart,
                                            @NonNull Integer dayNumberEnd);



}
