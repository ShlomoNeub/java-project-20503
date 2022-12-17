package com.project.db.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Shift {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;
    @Basic
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;
    @Basic
    @Column(name = "start_hour", nullable = false)
    private Integer startHour;
    @Basic
    @Column(name = "end_our", nullable = false)
    private Integer endOur;
    @Basic
    @Column(name = "emp_count", nullable = false)
    private Integer empCount;
    @OneToMany(mappedBy = "shiftByShiftId")
    private Collection<UserShift> userShiftsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndOur() {
        return endOur;
    }

    public void setEndOur(Integer endOur) {
        this.endOur = endOur;
    }

    public Integer getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return Objects.equals(id, shift.id) && Objects.equals(dayNumber, shift.dayNumber) && Objects.equals(weekNumber, shift.weekNumber) && Objects.equals(startHour, shift.startHour) && Objects.equals(endOur, shift.endOur) && Objects.equals(empCount, shift.empCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayNumber, weekNumber, startHour, endOur, empCount);
    }

    public Collection<UserShift> getUserShiftsById() {
        return userShiftsById;
    }

    public void setUserShiftsById(Collection<UserShift> userShiftsById) {
        this.userShiftsById = userShiftsById;
    }
}
