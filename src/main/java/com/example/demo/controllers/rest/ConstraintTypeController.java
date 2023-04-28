package com.example.demo.controllers.rest;

import com.example.demo.db.entities.ConstraintType;
import com.example.demo.db.repo.ConstraintTypeRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
    public Collection<ConstraintType> getAll() {
        return super.getAll();
    }

    @Override
    public ConstraintTypeRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }


}
