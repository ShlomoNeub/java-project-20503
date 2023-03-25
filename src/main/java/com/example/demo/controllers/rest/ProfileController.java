package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Profile;
import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.ProfileRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController extends RestApiAbstract<Profile, ProfileRepo,Integer>{
    final Logger logger = LogManager.getLogger(ProfileController.class);

    final ProfileRepo repo;

    public ProfileController(ProfileRepo repo) {
        this.repo = repo;
    }
    @RequestMapping(path = "/{id}/users",method = RequestMethod.GET)
    public Collection<String> getProfiles(@PathVariable Integer id){
        Optional<Profile> profile = repo.findById(id);
        if(profile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No profile with id"+id);
        }
        ArrayList<String> usersStrings = new ArrayList<>();
        for (Users _user : profile.get().getUsers()) {
            usersStrings.add(_user.getUsername());
        }

        return usersStrings;
    }
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ProfileRepo getRepo() {
        return repo;
    }


}
