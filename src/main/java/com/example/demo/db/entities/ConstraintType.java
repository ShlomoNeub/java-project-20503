package com.example.demo.db.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collection;

@Entity
public class ConstraintType implements Serializable {
    public static final int MAX_DESCRIPTION_LENGTH = 100;
    public static final int MIN_CONSTRAINT_LEVEL = 1;
    public static final int MAX_CONSTRAINT_LEVEL = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = MIN_CONSTRAINT_LEVEL, max = MAX_CONSTRAINT_LEVEL)
    private Integer constraintLevel;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private String description;

    @OneToMany(mappedBy = "constraintType")
    @JsonBackReference
    @Nullable
    private Collection<Constraint> constraints;

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public Integer getConstraintLevel() {
        return constraintLevel;
    }

    public void setConstraintLevel(@NotNull Integer constraintLevel) {
        this.constraintLevel = constraintLevel;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public boolean isValid() {
        boolean retVal = true;

        retVal &= constraintLevel <= MAX_CONSTRAINT_LEVEL;
        retVal &= MIN_CONSTRAINT_LEVEL <= constraintLevel;
        retVal &= description != null && !description.isEmpty() && description.length() <= MAX_DESCRIPTION_LENGTH;

        return retVal;
    }
}
