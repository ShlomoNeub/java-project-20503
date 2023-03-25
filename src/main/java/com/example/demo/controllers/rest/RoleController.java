package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Role;
import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.RoleRepo;
import com.example.demo.db.repo.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Controller that implements the Role REST API
 */
@RestController
@RequestMapping(path = "/roles")
public class RoleController extends RestApiAbstract<Role, RoleRepo,Integer> {

    final Logger logger = LogManager.getLogger(RoleController.class);
    final RoleRepo repo;

    public RoleController(RoleRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(path = "/{id}/role",method = RequestMethod.GET)
    public Collection<String> getProfiles(@PathVariable Integer id){
        Optional<Role> role = repo.findById(id);
        if(role.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No role with id"+id);
        }
        ArrayList<String> roleStrings = new ArrayList<>();
        for (Users _user : role.get().getUsers()) {
            roleStrings.add(_user.getUsername());
        }

        return roleStrings;
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
