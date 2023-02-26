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

    public List<T> getAll();

    public void insert(T u);

    public T update(T u);

    public void delete(T u);

    @Nullable
    public T getById(@NotNull long id);
}
