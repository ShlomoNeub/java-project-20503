package com.project.db.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Constraint_type", schema = "public", catalog = "project")
public class ConstraintType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "type_id", nullable = false)
    private Integer typeId;
    @Basic
    @Column(name = "constraint_level", nullable = false)
    private Integer constraintLevel;
    @Basic
    @Column(name = "constraint_description", nullable = false, length = -1)
    private String constraintDescription;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getConstraintLevel() {
        return constraintLevel;
    }

    public void setConstraintLevel(Integer constraintLevel) {
        this.constraintLevel = constraintLevel;
    }

    public String getConstraintDescription() {
        return constraintDescription;
    }

    public void setConstraintDescription(String constraintDescription) {
        this.constraintDescription = constraintDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstraintType that = (ConstraintType) o;
        return Objects.equals(typeId, that.typeId) && Objects.equals(constraintLevel, that.constraintLevel) && Objects.equals(constraintDescription, that.constraintDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, constraintLevel, constraintDescription);
    }
}
