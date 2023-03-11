package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.UserRepo;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.*;

/**
 * Controller that implements the Users REST API
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends RestApiAbstract<Users,UserRepo,Integer> {
    final Logger logger = LogManager.getLogger(UserController.class);
    final UserRepo repo;

    public UserController(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserRepo getRepo(){
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
