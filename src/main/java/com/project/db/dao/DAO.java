/**
 *
 */
package com.project.db.dao;


import com.sun.istack.internal.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Basic interface for the DataAccessObject design pattern for JPA
 * @param <T>
 */
public interface DAO <T>{

    /**
     * Get all entire of type T from the database
     * @return all the entries of type T
     */
    public List<T> getAll();

    /**
     * Insert entry of type T to the database
     * @param entry to insert
     */
    public void insert(T entry);

    /**
     * Update entry of type T in the database
     * @param entry to update
     * @return the updated entry
     */
    public T update(T entry);

    /**
     * Deletes an entry
     * @param entry to be deleted
     */
    public void delete(T entry);

    /**
     * Validate an entry
     * @param entry to validate
     * @return if the entry is valid
     */
    public boolean validate(T entry);


    /**
     * Get entry of type T from the database by its id
     * @param id of the entry
     * @return the entry requested or null if not found
     */
    @Nullable
    public T getById(@NotNull long id);
}
