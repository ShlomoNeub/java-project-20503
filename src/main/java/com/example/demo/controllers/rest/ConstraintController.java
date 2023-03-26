package com.example.demo.controllers.rest;


import com.example.demo.db.entities.Constraint;
import com.example.demo.db.entities.ConstraintType;
import com.example.demo.db.repo.ConstraintRepo;
import com.example.demo.db.repo.ConstraintTypeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/constraint")
public class ConstraintController extends RestApiAbstract<Constraint, ConstraintRepo,Integer>{

      public ConstraintRepo getRepo() {
        return null;
    }
    final Logger logger = Logger.getLogger(String.valueOf(ConstraintController.class));
    @Override
    public org.apache.logging.log4j.Logger getLogger() {
        return (org.apache.logging.log4j.Logger) logger;
    }
    final ConstraintRepo repo;
    public ConstraintController(ConstraintRepo repo) {
        this.repo = repo;
    }
    // get constraint by id
    @RequestMapping(path = "/{id}/constraint",method = RequestMethod.GET)
    public String getConstraints(@PathVariable Integer id){
        Optional<Constraint> constraint = repo.findById(id);
        if(constraint.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"NO ID IS FOUND"+id);
        }
        return constraint.toString();
    }
    // create new constraint
    @RequestMapping(path = "/{id}/constraint",method = RequestMethod.POST)
    public String createConstraint(@PathVariable Integer id){
        Optional<Constraint> constraint = repo.findById(id);
        if(constraint.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"NO ID IS FOUND"+id);
        }
        return constraint.toString();
    }






}
