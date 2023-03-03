package com.project.db.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Schedule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "schedule_id", nullable = false)
    private Integer scheduleId;
    @Basic
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;
    @Basic
    @Column(name = "request_id", nullable = false)
    private Integer requestId;
    @ManyToOne
    @JoinColumn(
            name = "request_id",
            referencedColumnName = "Request_id",
            nullable = false,
            updatable = false,
            insertable = false)
    private ShiftsRequests shiftsRequestsByRequestId;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(scheduleId, schedule.scheduleId) && Objects.equals(weekNumber, schedule.weekNumber) && Objects.equals(requestId, schedule.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, weekNumber, requestId);
    }

    public ShiftsRequests getShiftsRequestsByRequestId() {
        return shiftsRequestsByRequestId;
    }

    public void setShiftsRequestsByRequestId(ShiftsRequests shiftsRequestsByRequestId) {
        this.shiftsRequestsByRequestId = shiftsRequestsByRequestId;
    }
}
