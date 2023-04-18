package com.example.demo.db.entities;

import com.example.demo.config.annotation.ExcludeGson;
import com.example.demo.db.entities.interfaces.IEntity;
import com.example.demo.db.entities.interfaces.Validatable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

@Entity
public class AvailableShifts implements Serializable , Comparable<AvailableShifts>, IEntity<AvailableShifts,Integer> , Validatable<AvailableShifts> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer weekNumber;

    Integer dayNumber;

    Integer startHour;

    Integer duration;

    Integer employeeCount;

    Integer year;


    @OneToMany(mappedBy = "shift")
    @JsonBackReference
    @Nullable
    @ExcludeGson
    Collection<ShiftsRequests> requests;

    public Collection<ShiftsRequests> getRequests() {
        return requests;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

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


    public Integer getYear(){
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public boolean isValid() {
        boolean retVal = true;
        retVal &= 0 <= this.startHour ; // min is 0
        retVal &= this.startHour < 24; // max is 23
        retVal &= this.getEmployeeCount() > 0;
        retVal &= this.getDuration() > 0; // min is
        retVal &= this.weekNumber >= 0; // min is 0
        retVal &= this.year == Calendar.YEAR;
        return retVal;
    }


    @Override
    public boolean isValid(AvailableShifts toValidate) {
        return toValidate.isValid();
    }

    @Override
    public String toString() {
        return "AvailableShifts{" +
                "id=" + id +
                ", year=" + year +
                ", weekNumber=" + weekNumber +
                ", dayNumber=" + dayNumber +
                ", startHour=" + startHour +
                ", duration=" + duration +
                ", employeeCount=" + employeeCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableShifts that = (AvailableShifts) o;
        return Objects.equals(id, that.id) && Objects.equals(weekNumber, that.weekNumber) && Objects.equals(dayNumber, that.dayNumber)
                && Objects.equals(startHour, that.startHour) && Objects.equals(duration, that.duration)
                && Objects.equals(employeeCount, that.employeeCount) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekNumber, dayNumber, startHour, duration, employeeCount, year);
    }

    @Override
    public int compareTo(@NotNull AvailableShifts o) {
        if(this.equals(o)) return 0;
        if(!this.year.equals(o.year))
            return this.year.compareTo(o.year);
        if(!this.weekNumber.equals(o.weekNumber))
            return this.weekNumber.compareTo(o.weekNumber);
        if(!this.dayNumber.equals(o.dayNumber))
            return dayNumber.compareTo(o.dayNumber);
        if(!this.startHour.equals(o.startHour))
            return this.startHour.compareTo(o.startHour);
        if(!this.duration.equals(o.duration))
            return this.duration.compareTo(o.duration);
        if(!this.employeeCount.equals(o.employeeCount))
            return this.employeeCount.compareTo(o.employeeCount);
        if(!this.id.equals(o.id))
            return this.id.compareTo(o.id);
        return 1;
    }


}


