package com.example.demo.db.repo;

import com.example.demo.db.entities.AvailableShifts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface AvailableShiftsRepo extends CrudRepository<AvailableShifts, Integer> {

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
    @Query("select a from AvailableShifts a " +
            "where a.weekNumber between ?1 and ?2 and" +
            "(a.dayNumber between ?3 and ?4 OR a.dayNumber between ?4 and ?3)")
    Collection<AvailableShifts> findShiftsInRange(@NonNull Integer weekNumberStart,
                                                  @NonNull Integer weekNumberEnd,
                                                  @NonNull Integer dayNumberStart,
                                                  @NonNull Integer dayNumberEnd);


}
