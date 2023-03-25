package com.example.demo.db.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class ShiftsRequests implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "shift_id",insertable = false,updatable = false)
    private Integer shiftId;

    @Basic
    @Column(name = "uid",insertable = false,updatable = false)
    private Integer uid;

    @ManyToOne
    @JoinColumn(name = "uid",referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "shift_id",referencedColumnName = "id")
    private AvailableShifts shift;
    @Basic
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    java.sql.Timestamp timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftsRequests that)) return false;
        return  getId().equals(that.getId()) &&
                getUser().equals(that.getUser()) &&
                getShift().equals(that.getShift()) &&
                getTimestamp().equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getShift(), getTimestamp());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public AvailableShifts getShift() {
        return shift;
    }

    public void setShift(AvailableShifts shift) {
        this.shift = shift;
    }

    public boolean isValid() {

        return true;
    }
}
