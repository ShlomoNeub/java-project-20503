/**
 *
 */
package com.project.db.dao;


import java.util.List;

/**
 * Basic interface for the DataAccessObject design pattern for JPA
 * @param <T>
 */
public interface DAO <T>{

    public List<T> getAll();

    public void insert(T u);

    public void update(T u);

    public void delete(T u);
}
