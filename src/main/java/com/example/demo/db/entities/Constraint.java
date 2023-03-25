package com.example.demo.db.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "constraints")
public class Constraint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(insertable = false,updatable = false)
    private Integer typeId;

    private boolean isPermanent;

    private java.sql.Date startDate;

    private java.sql.Date endDate;


    @ManyToOne
    @JoinColumn(name = "typeId",referencedColumnName = "id", columnDefinition = "type_id")
    ConstraintType constraintType;


    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    @Nullable
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(@NotNull  Integer typeId) {
        this.typeId = typeId;
    }

    @Nullable
    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(@NotNull boolean permanent) {
        isPermanent = permanent;
    }

    @Nullable
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull  Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Date endDate) {
        this.endDate = endDate;
    }


    public boolean isValid() {
        boolean retVal = true;
        retVal &= this.startDate.before(this.endDate);

        return retVal;
    }
}
