package com.example.demo.db.entities.interfaces;

import java.io.Serializable;

public interface IEntity<Entity,IndexType> extends Comparable<Entity>, Serializable, Validatable<Entity> {
    IndexType getId();
    void setId(IndexType id);


}
