package com.example.demo.scheduler;

import com.example.demo.db.entities.AvailableShifts;
import com.example.demo.db.entities.Constraint;
import com.example.demo.db.entities.Schedule;
import com.example.demo.db.entities.User;
import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "JobStorage")
public class Job implements IEntity<Job, Integer> {
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
    public boolean isValid(Job toValidate) {
        return false;
    }

    @Override
    public int compareTo(Job o) {
        return 0;
    }
}
