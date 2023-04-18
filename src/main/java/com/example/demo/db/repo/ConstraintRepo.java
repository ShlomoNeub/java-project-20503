package com.example.demo.db.repo;

import com.example.demo.db.entities.Constraint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ConstraintRepo extends CrudRepository<Constraint, Integer> {
    // Find all constraints with a specific type
    @Query("SELECT c FROM Constraint c WHERE c.typeId=?1")
    List<Constraint> findByTypeId(Integer typeId);


    //find all constraints with a specific user in a given week
    @Query("SELECT c FROM Constraint c WHERE c.userId=?1 AND c.weekNumber=?2")
    List<Constraint> findByUserIdAndWeekNumber(Integer userId, Integer weekNumber);

    //find all constraints by type and week
    @Query("SELECT c FROM Constraint c WHERE c.typeId=?1 AND c.weekNumber=?2")
    List<Constraint> findByTypeIdAndWeekNumber(Integer typeId, Integer weekNumber);


    // Find all permanent constraints
    @Query("SELECT c FROM Constraint c WHERE c.isPermanent=true")
    List<Constraint> findByIsPermanentTrue();

    // Find all non-permanent constraints
    @Query("SELECT c FROM Constraint c WHERE c.isPermanent=false")
    List<Constraint> findByIsPermanentFalse();

    // Find all constraints that start within a date range
    @Query("SELECT c FROM Constraint c WHERE c.startDate>=?1 AND c.startDate<=?2")
    List<Constraint> findStartBetween(Date startDate, Date endDate);

    // Find all constraints that end within a date range
    @Query("SELECT c FROM Constraint c WHERE c.endDate>=?1 AND c.endDate<=?2")
    List<Constraint> findEndBetween(Date startDate, Date endDate);

    // Find all constraints that begin and finish between a date range
    @Query("SELECT c FROM Constraint c WHERE c.startDate>=?1 AND c.endDate<=?2")
    List<Constraint> findInRange(Date startDate, Date endDate);

    // Find all constraints that overlap with a date range
    @Query("SELECT c FROM Constraint c WHERE c.startDate<=?2 OR c.endDate>=?1")
    List<Constraint> findConstraintsOverlapInRange(Date startDate, Date endDate);


}
