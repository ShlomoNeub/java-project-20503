package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.example.demo.db.entities.interfaces.Validatable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "constraints")
public class Constraint implements IEntity<Constraint, Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer weekNumber;
    private String data;

    @Basic
    private Integer typeId;

    @Basic
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    private boolean isPermanent;

    private java.sql.Date startDate;

    private java.sql.Date endDate;


    @ManyToOne
    @JoinColumn(
            name = "userId",
            referencedColumnName = "id",
            columnDefinition = "user_id",
            insertable = false,
            updatable = false)
    User users;

    @ManyToOne
    @JoinColumn(name = "typeId",
            referencedColumnName = "id",
            columnDefinition = "type_id",
            insertable = false,
            updatable = false)
    ConstraintType constraintType;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(@NotNull Integer typeId) {
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

    public void setStartDate(@NotNull Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Date endDate) {
        this.endDate = endDate;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public User getUsers() {
        return users;
    }

    public boolean isValid() {
        boolean retVal = true;
        retVal &= this.startDate.before(this.endDate);

        return retVal;
    }

    @Override
    public int compareTo(@NotNull Constraint o) {
        try {
            return this.constraintType.getConstraintLevel().compareTo(o.constraintType.getConstraintLevel());
        } catch (NullPointerException e) {
            return 1;
        }
    }

    @Override
    public boolean isValid(Constraint toValidate) {
        return toValidate.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return isPermanent == that.isPermanent && Objects.equals(id, that.id) && Objects.equals(weekNumber, that.weekNumber) && Objects.equals(data, that.data) && Objects.equals(typeId, that.typeId) && Objects.equals(userId, that.userId) && Objects.equals(users, that.users) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(constraintType, that.constraintType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekNumber, data, typeId, userId, users, isPermanent, startDate, endDate, constraintType);
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "id=" + id +
                ", weekNumber=" + weekNumber +
                ", data='" + data + '\'' +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", users=" + users +
                ", isPermanent=" + isPermanent +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", constraintType=" + constraintType +
                '}';
    }
}

