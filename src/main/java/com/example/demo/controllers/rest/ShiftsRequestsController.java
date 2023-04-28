package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.db.entities.ShiftsRequests;
import com.example.demo.db.entities.User;
import com.example.demo.db.repo.ShiftRequestRepo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

/**
 * Controller that implements the Schedule REST API
 */
@RestController
@RequestMapping(path = "/shiftsrequests")
public class ShiftsRequestsController extends RestApiAbstract<ShiftsRequests, ShiftRequestRepo, Integer> {

    final Logger logger = LogManager.getLogger(ShiftsRequestsController.class);
    final ShiftRequestRepo repo;

    @GetMapping(path = "/admin")
    public String getAllRequestsAdmin(){
        Collection<ShiftsRequests> shiftsRequestsCollection = super.getAll();
        JsonArray array = new JsonArray();
        for (ShiftsRequests s:shiftsRequestsCollection
             ) {
            JsonObject jsonObject = JsonParser.parseString(gson.toJson(s)).getAsJsonObject();
            jsonObject.add("user", JsonParser.parseString(gson.toJson(s.getUser())).getAsJsonObject());
            array.add(jsonObject);
        }
        return array.toString();
    }


    @Override
    @Auth
    public ShiftsRequests createNewEntity(ShiftsRequests entity) {
        ShiftsRequests requests = repo.findByUidAndShiftId(entity.getUid(), entity.getShiftId()).orElse(null);
        if (requests != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already submitted");
        } else {
            return super.createNewEntity(entity);
        }
    }

    public ShiftsRequestsController(ShiftRequestRepo repo) {
        this.repo = repo;
    }

    @Override
    public ShiftRequestRepo getRepo() {
        return repo;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }


}
