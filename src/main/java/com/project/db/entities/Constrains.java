package com.project.db.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Constrains {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "constraint_id", nullable = false)
    private Integer constraintId;
    @Basic
    @Column(name = "type_id", nullable = false)
    private Integer typeId;
    @Basic
    @Column(name = "uid", nullable = false)
    private Integer uid;
    @Basic
    @Column(name = "data", nullable = false)
    private Integer data;
    @Basic
    @Column(name = "permanent_flag", nullable = false)
    private Boolean permanentFlag;
    @Basic
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Integer startDate;
    @Basic
    @Column(name = "end_date", nullable = false)
    private Integer endDate;

    public Integer getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(Integer constraintId) {
        this.constraintId = constraintId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Boolean getPermanentFlag() {
        return permanentFlag;
    }

    public void setPermanentFlag(Boolean permanentFlag) {
        this.permanentFlag = permanentFlag;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constrains that = (Constrains) o;
        return Objects.equals(constraintId, that.constraintId) && Objects.equals(typeId, that.typeId) && Objects.equals(uid, that.uid) && Objects.equals(data, that.data) && Objects.equals(permanentFlag, that.permanentFlag) && Objects.equals(weekNumber, that.weekNumber) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constraintId, typeId, uid, data, permanentFlag, weekNumber, startDate, endDate);
    }
}
