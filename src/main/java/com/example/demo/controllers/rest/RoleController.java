package com.example.demo.controllers.rest;

import com.example.demo.db.entities.*;
import com.example.demo.db.repo.RoleRepo;
import org.apache.logging.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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

    /**
     * <b>GET /{id}/role</b>
     * <p>Get all the usernames with this role related to the given role-id</p>
     *
     * @param id of the desired role
     * @return JsonArray with usernames with the given role
     * @throws ResponseStatusException when could not find any users with the role
     */
    @RequestMapping(
            path = "/{id}/role",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public Collection<String> getProfiles(@PathVariable Integer id) {
        Role role = repo.findById(id).orElse(null);
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role with id" + id);
        }
        ArrayList<String> roleStrings = new ArrayList<>();
        for (User _user : role.getUsers()) {
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
