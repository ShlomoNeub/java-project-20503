package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class ShiftsRequests implements Serializable , IEntity<ShiftsRequests,Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "shift_id")
    private Integer shiftId;

    @Basic
    @Column(name = "uid")
    private Integer uid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AvailableShifts shift;


    @Basic
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    java.sql.Timestamp timestamp;


    @ManyToOne
    @JoinColumn(name="job_id",referencedColumnName = "id")
    private ScheduleJob scheduleJob;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "request", orphanRemoval = true)
    private Schedule schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftsRequests that)) return false;
        return getId().equals(that.getId()) &&
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.uid = user.getId();
        this.user = user;

    }

    public AvailableShifts getShift() {
        return shift;
    }

    public void setShift(AvailableShifts shift) {
        this.shiftId = shift.id;
        this.shift = shift;
    }

    public ScheduleJob getScheduleJob() {
        return scheduleJob;
    }

    public void setScheduleJob(ScheduleJob scheduleJob) {
        this.scheduleJob = scheduleJob;
    }

    @Override
    public boolean isValid(ShiftsRequests toValidate) {
        // TODO: Think about what really needs to be validate when adding entry to schedule
        return true;
    }

    @Override
    public int compareTo(ShiftsRequests o) {
        try {
            return this.equals(o)?0:this.id.compareTo(o.id);
        }catch (Exception e){
            return 1;
        }
    }

    @Override
    public String toString() {
        return "ShiftsRequests{" +
                "id=" + id +
                ", shiftId='" + shiftId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
