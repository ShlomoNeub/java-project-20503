package com.example.demo.db.repo;

import com.example.demo.db.entities.ConstraintType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConstraintTypeRepo extends CrudRepository<ConstraintType,Integer> {

    // Find all constraint types with a specific constraint level
    @Query("SELECT c FROM ConstraintType c WHERE c.constraintLevel=?1")
    List<ConstraintType> findByConstraintLevel(Integer constraintLevel);

    // Find all constraint types with a constraint level less than or equal to a specified value
    @Query("SELECT c FROM ConstraintType c WHERE c.constraintLevel<=?1")
    List<ConstraintType> findByConstLevelLessEqual(Integer constraintLevel);

    // Find all constraint types with a constraint level greater than or equal to a specified value
    @Query("SELECT c FROM ConstraintType c WHERE c.constraintLevel>=?1")
    List<ConstraintType> findByConstLevelGreaterEqual(Integer constraintLevel);

}
