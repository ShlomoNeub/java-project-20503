package com.example.demo.db.repo;

import com.example.demo.db.entities.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule, Integer> {
    /**
     * Retrieves all schedules that were created for a shift
     *
     * @param shiftId of the target
     * @return All the scheduled if there any
     */
    @Query("select s from Schedule s where s.request.shiftId = ?1")
    Collection<Schedule> findByRequestShiftId(Integer shiftId);


    /**
     * Gets num of scheduled workers by shift id
     *
     * @param id of request
     * @return Count of scheduled wirkers for each shift
     */
    @Query("select count(s) from Schedule s where s.request.shift.id = ?1")
    long countEmploieesInShift(Integer id);


    /**
     * @see #findByRequestShiftId
     */
    @Query("select s from Schedule s where s.request.shiftId = ?1")
    Collection<Schedule> findByShiftId(Integer shiftId);

    @Query("""
            select s from Schedule s
            where s.request.user.id = ?1 and s.request.shift.weekNumber = ?2 and s.request.shift.dayNumber = ?3""")
    List<Schedule> findByUsersInRange(Integer id, @Nullable Integer weekNumber, @Nullable Integer dayNumber);


}
