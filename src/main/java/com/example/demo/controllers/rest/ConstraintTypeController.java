package com.example.demo.controllers.rest;


import com.example.demo.db.entities.Constraint;
import com.example.demo.db.entities.ConstraintType;
import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.ConstraintTypeRepo;
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
@RequestMapping(path = "/constraintType")
    public class ConstraintTypeController extends RestApiAbstract<ConstraintType, ConstraintTypeRepo,Integer>{
/*

    @RequestMapping(path = "/{id}/constraint-type",method = RequestMethod.GET)
    public Collection<String> getConstraintsType(@PathVariable Integer id){
        Optional<ConstraintType> constraintType = repo.findById(id);
        if(constraintType.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no v"+id);
        }
        ArrayList<String> constraintTypeStrings = new ArrayList<>();
        for (ConstraintType _constraintType : constraintType.get().getConstraintType()) {
            constraintTypeStrings.add(_constraintType.getConstraintTypeDescr());
        }
        return constraintTypeStrings;
    }
    */
    @RequestMapping(path = "/{id}/constraint-type",method = RequestMethod.GET)
    public String getConstraintsType(@PathVariable Integer id){
    Optional<ConstraintType> constraint = repo.findById(id);
    if(constraint.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"NO ID IS FOUND"+id);
    }
    return constraint.toString();
    }
    final Logger logger = LogManager.getLogger(ConstraintTypeController.class);
    @Override
    public Logger getLogger() {
        return logger;
    }

    final ConstraintTypeRepo repo;

    public ConstraintTypeController(ConstraintTypeRepo repo) {
        this.repo = repo;
    }
    @Override
    public ConstraintTypeRepo getRepo() {
        return null;
    }



}
