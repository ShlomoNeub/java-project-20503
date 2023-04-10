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
    List<Schedule> getSchedulesByWeekNumber(Integer week);

    /**
     * Gets Roles by Request
     * @param request
     * @return List
     */
    Schedule getScheduleByRequestId(int request);
}
