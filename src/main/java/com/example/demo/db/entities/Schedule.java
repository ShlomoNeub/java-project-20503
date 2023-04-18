package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Schedule implements Serializable , IEntity<Schedule,Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer weekNumber;
    @Column(name="request_id",insertable = false,updatable = false)
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "request_id",referencedColumnName = "id")
    ShiftsRequests request;

    public ShiftsRequests getRequest() {
        return request;
    }
    public void setRequest(ShiftsRequests request){
        this.request=request;
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
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.getId()) && Objects.equals(weekNumber, schedule.getWeekNumber()) && Objects.equals(requestId, schedule.getRequestId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekNumber, requestId);
    }


    @Override
    public boolean isValid(Schedule toValidate) {
        // TODO: Think about what really needs to be validate when adding entry to schedule
        return true;
    }

    @Override
    public int compareTo(Schedule o) {
        try {
            return this.equals(o)?0:this.id.compareTo(o.id);
        }catch (Exception e){
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", requestId='" + requestId + '\'' +
                ", weekNumber='" + weekNumber + '\'' +
                ", user='" + request.getUser() + '\'' +
                ", shift='" + request.getShift() + '\'' +

                '}';
    }
}
