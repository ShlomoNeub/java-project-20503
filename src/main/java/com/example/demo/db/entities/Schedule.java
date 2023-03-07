package com.example.demo.db.entities;

import com.example.demo.db.Validatable;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Schedule implements Serializable {
    @Id
    Integer Id;

    Integer weekNumber;
    @Column(name="request_id",insertable = false,updatable = false)
    Integer requestId;

    @ManyToOne
    @JoinColumn(name = "request_id",referencedColumnName = "id")
    ShiftsRequests request;

    public ShiftsRequests getRequest() {
        return request;
    }

    public void setRequest(ShiftsRequests request) {
        this.request = request;
    }

    public boolean isValid() {
        // TODO: Think about what really needs to be validate when adding entry to schedule
        return true;
    }
}
