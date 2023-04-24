package com.example.demo.db.repo;

import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.entities.ScheduleJob;
import com.example.demo.db.entities.ShiftsRequests;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule,Integer> {
    /**
     * Retrieves all schedules that were created for a shift
     * @param shiftId of the target
     * @return All the scheduled if there any
     */
    @Query("select s from Schedule s where s.request.shiftId = ?1")
    Collection<Schedule> findByRequestShiftId(Integer shiftId);

    /**
     * Gets Schedule by week
     * @param week
     * @return List
     */
    List<Schedule> getSchedulesByWeekNumber(Integer week);

    /**
     * Gets Roles by Request
     * @param request
     * @return List
     */
    Schedule getScheduleByRequestId(int request);

    /**
     * Gets num of scheduled workers by shift id
     * @param id of request
     * @return Count of scheduled wirkers for each shift
     * */
    @Query("select count(s) from Schedule s where s.request.shift.id = ?1")
    long countEmploieesInShift(Integer id);



    /**
     * @see #findByRequestShiftId
     */
    @Query("select s from Schedule s where s.request.shiftId = ?1")
    Collection<Schedule> findByShiftId(Integer shiftId);

    /**
     * @see #findByRequestShiftId
     */
    @Query("select s from Schedule s where s.request.shift.id in ?1")
    Collection<Schedule> findByRequest_Shift_IdIn(Collection<Integer> ids);


    @Query("SELECT s from Schedule s where s.request.scheduleJob IS NOT NULL AND s.request.scheduleJob.userId = ?1")
    Collection<Schedule> getScheduleJobsByJobId(Integer userId);



}
