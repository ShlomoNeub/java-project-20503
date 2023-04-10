package com.example.demo.controllers.rest;

import com.example.demo.db.entities.ConstraintType;
import com.example.demo.db.repo.ConstraintTypeRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller that implements the ConstraintType REST API
 */
@RestController
@RequestMapping(path = "/constraint-types")
public class ConstraintTypeController extends RestApiAbstract<ConstraintType, ConstraintTypeRepo, Integer> {
    final Logger logger = LogManager.getLogger(ConstraintTypeController.class);
    final ConstraintTypeRepo repo;

    public ConstraintTypeController(ConstraintTypeRepo repo) {
        this.repo = repo;
    }

    @Override
    public ConstraintTypeRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * Find all constraint types with a specific constraint level
     * @param constraintLevel the constraint level to search for
     * @return a list of constraint types with the given constraint level
     */
    @GetMapping("/level/{constraintLevel}")
    public List<ConstraintType> findByConstraintLevel(@PathVariable Integer constraintLevel) {
        List<ConstraintType> constraintTypes = repo.findByConstraintLevel(constraintLevel);
        if (constraintTypes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraint types with the specified level were found.");
        }
        return constraintTypes;
    }

    /**
     * Find all constraint types with a constraint level less than or equal to a specified value
     * @param constraintLevel the constraint level to search for
     * @return a list of constraint types with a constraint level less than or equal to the specified value
     */
    @GetMapping("/level/less-equal/{constraintLevel}")
    public List<ConstraintType> findByConstLevelLessEqual(@PathVariable Integer constraintLevel) {
        List<ConstraintType> constraintTypes = repo.findByConstLevelLessEqual(constraintLevel);
        if (constraintTypes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraint types with the specified level or lower were found.");
        }
        return constraintTypes;
    }

    /**
     * Find all constraint types with a constraint level greater than or equal to a specified value
     * @param constraintLevel the constraint level to search for
     * @return a list of constraint types with a constraint level greater than or equal to the specified value
     */
    @GetMapping("/level/greater-equal/{constraintLevel}")
    public List<ConstraintType> findByConstLevelGreaterEqual(@PathVariable Integer constraintLevel) {
        List<ConstraintType> constraintTypes = repo.findByConstLevelGreaterEqual(constraintLevel);
        if (constraintTypes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraint types with the specified level or higher were found.");
        }
        return constraintTypes;
    }
}
