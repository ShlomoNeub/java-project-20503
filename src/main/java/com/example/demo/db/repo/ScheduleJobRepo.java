package com.example.demo.db.repo;

import com.example.demo.db.entities.ScheduleJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;


public interface ScheduleJobRepo extends CrudRepository<ScheduleJob, Integer> {
    @Query("select s from ScheduleJob s where s.userId = ?1")
    Collection<ScheduleJob> findByUserId(Integer userId);

    /**
     * Gets all the jobs that are not done yet
     *
     * @return undone jobs
     */
    @Query("select j from ScheduleJob j where j.done = false")
    Collection<ScheduleJob> findByNotDone();


}
