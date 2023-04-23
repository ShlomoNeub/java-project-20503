package com.example.demo.controllers.rest;

import com.example.demo.config.annotation.Auth;
import com.example.demo.config.annotation.PrivateGson;
import com.example.demo.config.interceptor.GsonExcludeStrategy;
import com.example.demo.db.entities.interfaces.IEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
public abstract class RestApiAbstract
        <
                Entity extends IEntity<Entity, IndexType>,
                Repo extends CrudRepository<Entity, IndexType>,
                IndexType extends Serializable
                >
        implements CrudAPI<Entity, IndexType> {


    protected static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new GsonExcludeStrategy()).create();
    protected static Gson privateGson = new GsonBuilder()
            .setExclusionStrategies(
                    new GsonExcludeStrategy(),
                    new GsonExcludeStrategy(PrivateGson.class)
            ).create();

    @Override
    @Auth
    public Collection<Entity> getAll() {
        getLogger().info("Get all has been called");
        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity e : getRepo().findAll()) {
            entities.add(e);
        }
        if (entities.size() == 0)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No entities");
        return entities;
    }

    @Override
    @Auth
    public Entity createNewEntity(Entity entity) {
        getLogger().info("Create new was called with " + entity);
        entity.setId(null);
        if (entity.isValid(entity)) {
            return saveEntity(entity);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid properties");
    }

    @Override
    @Auth
    public Entity getById(IndexType id) {
        getLogger().info("Get entity by id was called with id:" + id);
        Optional<Entity> e = getRepo().findById(id);
        if (e.isEmpty()) {
            getLogger().warn("Unable to find entity with id:" + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        return e.get();
    }

    @Override
    @Auth
    public Entity updateById(IndexType id, Entity entity) {
        getLogger().info("Update entity by id was called with id:" + id + " With:" + entity);
        Optional<Entity> entity1 = getRepo().findById(id);
        if (entity1.isEmpty()) {
            getLogger().warn("Unable to find entity with id:" + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        entity.setId(id);
        return saveEntity(entity);
    }

    @Override
    @Auth
    public void deleteByID(IndexType id) {
        getLogger().info("Delete entity by id was called with id:" + id);
        Optional<Entity> entity1 = getRepo().findById(id);
        if (entity1.isEmpty()) {
            getLogger().warn("Unable to find entity with id:" + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        getRepo().delete(entity1.get());
    }

    @NotNull
    public abstract Repo getRepo();

    public Logger getLogger() {
        return LogManager.getLogger(RestApiAbstract.class);
    }

    /**
     * Saves and handle the saving proccess of the entity
     *
     * @param entity to be saved
     * @return saved entity
     * @throws ResponseStatusException if could not be saved/update
     */
    @NotNull
    private Entity saveEntity(@NotNull Entity entity) {
        try {
            return getRepo().save(entity);
        } catch (DataIntegrityViolationException e) {
            getLogger().error("Error while handling insert reason:" + e.getMostSpecificCause());
            getLogger().debug(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid properties");
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to handle request");
        }
    }

}
