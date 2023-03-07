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

    protected Repo repo;

    public RestApiAbstract() {
    }

    @Override
    public Collection<Entity> getAll() {
        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity e : getRepo().findAll()) {
            entities.add(e);
        }
        return entities;
    }

    @Override
    public Entity createNewEntity(Entity entity) {
        if(isValid(entity)){
            return getRepo().save(entity);
        }
        return null;
    }

    @Override
    public Entity getById(Integer id) {
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

    public abstract Repo getRepo();

}
