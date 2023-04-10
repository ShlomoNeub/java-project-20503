/**
 * Here we defined the basic information needed by any entity
 */
package com.example.demo.db.entities.interfaces;

import java.io.Serializable;

/**
 * This interface help "basing" th differences between every JDBC Entity
 * @param <Entity> JDBC entity
 * @param <IndexType> The index type of the entity
 */
public interface IEntity<Entity,IndexType> extends Comparable<Entity>, Serializable, Validatable<Entity> {
    IndexType getId();
    void setId(IndexType id);


}
