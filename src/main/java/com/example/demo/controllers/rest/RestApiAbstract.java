package com.example.demo.controllers.rest;

import com.example.demo.db.Validatable;
import jakarta.annotation.*;
import org.apache.logging.log4j.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public abstract class RestApiAbstract
        <Entity,Repo extends CrudRepository<Entity,Integer>>
        implements CrudAPI<Entity>,Validatable<Entity> {


    public RestApiAbstract() {
    }

    @Override
    public Collection<Entity> getAll() {
        getLogger().info("Get all has been called");
        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity e : getRepo().findAll()) {
            entities.add(e);
        }
        return entities;
    }

    @Override
    public Entity createNewEntity(Entity entity) {
        getLogger().info("Create new was called with "+ entity);
        if(isValid(entity)){
            return getRepo().save(entity);
        }
        return null;
    }

    @Override
    public Entity getById(Integer id) {
        getLogger().info("Get entity by id was called with id:"+id);
        Optional<Entity> e= getRepo().findById(id);
        return e.orElse(null);
    }

    @Override
    public Entity updateById(Integer id, Entity entity) {
        return null;
    }

    @Override
    public boolean deleteByID(Integer id) {
        return false;
    }
    @Nullable
    public abstract Repo getRepo();
    public Logger getLogger(){
        return  LogManager.getLogger(UserController.class);
    }

}
