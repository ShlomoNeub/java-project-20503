package com.example.demo.db.repo;

import com.example.demo.db.entities.Constraint;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConstraintRepo extends CrudRepository<Constraint, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Constraint c where c.userId = ?1 and c.id = ?2")
    int deleteByUserAndId(Integer userId, Integer id);

    @Query("select c from Constraint c where c.userId = ?1 and " +
            "(" +
            "(?2 BETWEEN c.startDate AND c.endDate) " +
            "OR" +
            "(?3 BETWEEN c.startDate AND c.endDate)" +
            ")" +
            " ")
    List<Constraint> findByDateRangeAndUser(Integer userId, java.sql.Date startDate, java.sql.Date endDate);

    @Query("select c from Constraint c where c.userId = ?1 and (c.weekNumber = ?2 or c.weekNumber = ?3)")
    List<Constraint> findByWeekRangeAndUser(Integer userId, Integer startW, Integer endWeek);


}
