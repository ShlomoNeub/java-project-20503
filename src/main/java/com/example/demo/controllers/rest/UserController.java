package com.example.demo.controllers.rest;

import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.ProfileRepo;
import com.example.demo.db.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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

    @RequestMapping(path = "/{id}/user-profiles",method = RequestMethod.GET)
    public Collection<String> getProfiles(@PathVariable Integer id){
        Optional<Users> user = repo.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No user with id"+id);
        }
        ArrayList<String> usersStrings = new ArrayList<>();
        for (Users _user : user.get().getProfile().getUsers()) {
            usersStrings.add(_user.getUsername());
        }

        return usersStrings;
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
