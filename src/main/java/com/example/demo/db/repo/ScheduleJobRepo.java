package com.example.demo.scheduler;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface JobRepo extends CrudRepository<Job,Integer> {
    @Query("select j from Job j where j.done = false")
    Collection<Job> findByNotDone();



}
