package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Role;
import com.example.demo.db.repo.RoleRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that implements the Role REST API
 */
@RestController
@RequestMapping(path = "/roles")
public class RoleController extends RestApiAbstract<Role, RoleRepo, Integer> {

    final Logger logger = LogManager.getLogger(RoleController.class);
    final RoleRepo repo;

    public RoleController(RoleRepo repo) {
        this.repo = repo;
    }

    @Override
    public RoleRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
