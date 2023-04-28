package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Objects;

@Entity
public class ConstraintType implements IEntity<ConstraintType, Integer> {
    public static final int MAX_DESCRIPTION_LENGTH = 100;
    public static final int MIN_CONSTRAINT_LEVEL = 1;
    public static final int MAX_CONSTRAINT_LEVEL = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(MIN_CONSTRAINT_LEVEL)
    @Max(MAX_CONSTRAINT_LEVEL)
    private Integer constraintLevel;


    @Size(max = MAX_DESCRIPTION_LENGTH)
    private String description;

    @OneToMany(mappedBy = "constraintType")
    @JsonBackReference
    @Nullable
    private Collection<Constraint> constraints;

    public Collection<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Collection<Constraint> constraints) {
        this.constraints = constraints;
    }

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

    @Override
    public boolean isValid(ConstraintType toValidate) {
        return toValidate.isValid();
    }


    @Override
    public String toString() {
        return "ConstraintType{" +
                "id=" + id +
                ", constraintLevel=" + constraintLevel +
                ", description='" + description + '\'' +
                ", constraints=" + constraints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstraintType that = (ConstraintType) o;
        return Objects.equals(id, that.id) && Objects.equals(constraintLevel, that.constraintLevel) && Objects.equals(description, that.description) && Objects.equals(constraints, that.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, constraintLevel, description, constraints);
    }

    @Override
    public int compareTo(@NotNull ConstraintType o) {

        try {
            return this.constraintLevel.compareTo(o.constraintLevel);
        } catch (Exception e) {
            return 1;
        }
    }
}
