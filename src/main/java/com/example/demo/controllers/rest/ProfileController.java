package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Profile;
import com.example.demo.db.repo.ProfileRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController extends RestApiAbstract<Profile, ProfileRepo, Integer> {
    final Logger logger = LogManager.getLogger(ProfileController.class);

    final ProfileRepo repo;

    public ProfileController(ProfileRepo repo) {
        this.repo = repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ProfileRepo getAvailableShiftsRepo() {
        return repo;
    }


}
