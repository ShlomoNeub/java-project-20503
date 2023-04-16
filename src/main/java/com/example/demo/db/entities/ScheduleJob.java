package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class ScheduleJob implements IEntity<ScheduleJob, Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    java.sql.Date startDate;

    java.sql.Date endDate;

    Boolean done = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


    @Override
    public boolean isValid(ScheduleJob toValidate) {
        return false;
    }

    @Override
    public int compareTo(ScheduleJob o) {
        return 0;
    }
}
