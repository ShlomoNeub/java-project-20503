package com.example.demo.db.repo;

import com.example.demo.db.entities.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule,Integer> {

    /**
     * Gets Schedule by week
     * @param week
     * @return List
     */
            List<Schedule> getSchedulesByWeekNumber(Integer weekNumber);

    /**
     * Gets Roles by Request
     * @param request
     * @return List
     */
    @Query("SELECT s FROM Schedule s WHERE s.requestId = ?1 ORDER BY s.requestId")
    Schedule getScheduleByRequestId(int request);
}
