/**
 * This file have the basic implementation for all CRUD based controllers
 */
package com.example.demo.controllers.rest;

import com.example.demo.db.entities.interfaces.IEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interface for generic CRUD REST-Full api and the basic path
 *
 * @param <T>         Domain type that need to manage
 * @param <IndexType> the type of the id of the entity the repository manage
 * @author Shlomo Neuberger
 */
@RestController
public interface CrudAPI<T extends IEntity<T, IndexType>, IndexType extends Serializable> {
    /**
     * GET /
     * <p>Get all entire of the object</p>
     *
     * @return all the entities of the manged object
     * @throws ResponseStatusException when empty result
     * @apiNote retries all the object of the manged domain
     */
    @RequestMapping(path = "/")
    Collection<T> getAll() throws ResponseStatusException;

    /**
     * POST /
     * <p>Creates a new entity</p>
     *
     * @param entity to be created
     * @return the created entity if create
     * @throws ResponseStatusException on creation error
     * @apiNote this method will create new entity in the database
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    T createNewEntity(@RequestBody T entity) throws ResponseStatusException;

    /**
     * GET /{id}
     * <p>Retries an object by id</p>
     *
     * @param id of the requested object
     * @return the requested entity
     * @throws ResponseStatusException if entity not found
     * @apiNote retire an object based on the id
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    T getById(@PathVariable(value = "id") IndexType id) throws ResponseStatusException;

    /**
     * PUT /{id}
     * <p>update the a given object by id</p>
     *
     * @param id     of the object to update
     * @param entity the object with the information to update
     * @return the newly updated object
     * @throws ResponseStatusException when cannot update the objects or when user not found
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    T updateById(@PathVariable(value = "id") IndexType id, @RequestBody T entity) throws ResponseStatusException;

    /**
     * DELETE /{id}
     * <p>Deletes object by id</p>
     *
     * @param id to be deleted
     * @throws ResponseStatusException if the object was not be able to be deleted
     * @apiNote call to delete an object
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    void deleteByID(@PathVariable(value = "id") IndexType id) throws ResponseStatusException;


}
