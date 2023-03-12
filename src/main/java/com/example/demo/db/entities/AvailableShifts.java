package com.example.demo.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class AvailableShifts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer weekNumber;

    Integer dayNumber;

    Integer startHour;

    Integer duration;

    Integer employeeCount;

    Integer mangerCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(@NotNull Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(@NotNull Integer duration) {
        this.duration = duration;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(@NotNull Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getMangerCount() {
        return mangerCount;
    }

    public void setMangerCount(@NotNull Integer mangerCount) {
        this.mangerCount = mangerCount;
    }

    public boolean isValid() {
        boolean retVal = true;
        retVal &= 0 <= this.startHour ; // min is 0
        retVal &= this.startHour < 24; // max is 23
        retVal &= this.getEmployeeCount() > 0 || this.getMangerCount() > 0; // at least 1 manger or 1 employee
        retVal &= this.getDuration() > 0; // min is
        retVal &= this.weekNumber >= 0; // min is 0

        return retVal;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvailableShifts that)) return false;
        return weekNumber.equals(that.weekNumber) && dayNumber.equals(that.dayNumber) && getStartHour().equals(that.getStartHour()) && getDuration().equals(that.getDuration());
    }


}
