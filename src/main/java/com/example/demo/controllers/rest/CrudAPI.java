package com.example.demo.controllers.rest;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public interface CrudAPI<T> {

    @RequestMapping(path = "/")
    Collection<T> getAll();

    @RequestMapping(path = "/",method = RequestMethod.POST)
    T createNewEntity(@RequestBody T entity);
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    T getById(@PathVariable(value = "id") Integer id);

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    T updateById(@PathVariable(value = "id") Integer id,@RequestBody T entity);

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    boolean deleteByID(@PathVariable(value = "id") Integer id);


}
