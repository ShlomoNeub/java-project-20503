package com.project.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Shifts_requests", schema = "public", catalog = "project")
public class ShiftsRequests {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Request_id", nullable = false)
    private Integer requestId;
    @Basic
    @Column(name = "shift_id", nullable = false)
    private Integer shiftId;
    @Basic
    @Column(name = "uid", nullable = false)
    private Integer uid;
    @Basic
    @Column(name = "time_stamp", nullable = false)
    private Timestamp timeStamp;
    @OneToMany(mappedBy = "shiftsRequestsByRequestId")
    private Collection<Schedule> schedulesByRequestId;
    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "shifts_id", nullable = false)
    private AvailableShifts availableShiftsByShiftId;
    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private User userByUid;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftsRequests that = (ShiftsRequests) o;
        return Objects.equals(requestId, that.requestId) && Objects.equals(shiftId, that.shiftId) && Objects.equals(uid, that.uid) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, shiftId, uid, timeStamp);
    }

    public Collection<Schedule> getSchedulesByRequestId() {
        return schedulesByRequestId;
    }

    public void setSchedulesByRequestId(Collection<Schedule> schedulesByRequestId) {
        this.schedulesByRequestId = schedulesByRequestId;
    }

    public AvailableShifts getAvailableShiftsByShiftId() {
        return availableShiftsByShiftId;
    }

    public void setAvailableShiftsByShiftId(AvailableShifts availableShiftsByShiftId) {
        this.availableShiftsByShiftId = availableShiftsByShiftId;
    }

    public User getUserByUid() {
        return userByUid;
    }

    public void setUserByUid(User userByUid) {
        this.userByUid = userByUid;
    }
}
