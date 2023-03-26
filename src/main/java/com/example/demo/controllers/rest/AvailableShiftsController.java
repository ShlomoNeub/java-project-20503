package com.example.demo.controllers.rest;


import com.example.demo.db.entities.AvailableShifts;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.db.repo.AvailableShiftsRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping(path = "/availableShifts")
public class AvailableShiftsController extends RestApiAbstract<AvailableShifts, AvailableShiftsRepo,Integer>{

    final  AvailableShiftsRepo repo;
    final Logger logger = Logger.getLogger(AvailableShiftsController.class .getName());

    @RequestMapping(path = "/{id}/availableShifts",method = RequestMethod.GET)
    public String getConstraints(@PathVariable Integer id){
        Optional<AvailableShifts> availableShifts = repo.findById(id);
        if(availableShifts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"NO ID IS FOUND"+id);
        }
        return availableShifts.toString();
    }


    @Override
    public AvailableShiftsRepo getRepo() {
        return repo;
    }
    public AvailableShiftsController(AvailableShiftsRepo availableShiftsRepo) {
        this.repo = availableShiftsRepo;
    }
    /*
@RequestMapping(path = "/{id}/availableShifts",method = RequestMethod.GET)
    public Collection<AvailableShifts> getAvailableShifts(@PathVariable Integer id) {
        Optional<AvailableShifts> availableShifts = repo.findById(id);
        if (availableShifts.isPresent()) {
            return availableShifts.get().getAvailableShifts();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AvailableShifts not found");
        }
    }

*/



}
