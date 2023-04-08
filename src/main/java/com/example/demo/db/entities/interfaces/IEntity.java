/**
 * Here we defined the basic information needed by any entity
 */
package com.example.demo.db.entities.interfaces;

import java.io.Serializable;

/**
 * This interface help "basing" th differences between every Entity
 * @param <Entity>
 * @param <IndexType>
 */
public interface IEntity<Entity,IndexType> extends Comparable<Entity>, Serializable, Validatable<Entity> {
    IndexType getId();
    void setId(IndexType id);


}
