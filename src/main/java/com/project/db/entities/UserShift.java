package com.project.db.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_shift", schema = "public", catalog = "project")
public class UserShift {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "profileId", nullable = false)
    private Integer profileId;
    @Basic
    @Column(name = "timestamp", nullable = false)
    private Integer timestamp;
    @Basic
    @Column(name = "shiftId", nullable = true)
    private Integer shiftId;
    @ManyToOne
    @JoinColumn(name = "profileId", referencedColumnName = "pid", nullable = false)
    private Profile profileByProfileId;
    @ManyToOne
    @JoinColumn(name = "shiftId", referencedColumnName = "id")
    private Shift shiftByShiftId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserShift userShift = (UserShift) o;
        return Objects.equals(id, userShift.id) && Objects.equals(profileId, userShift.profileId) && Objects.equals(timestamp, userShift.timestamp) && Objects.equals(shiftId, userShift.shiftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profileId, timestamp, shiftId);
    }

    public Profile getProfileByProfileId() {
        return profileByProfileId;
    }

    public void setProfileByProfileId(Profile profileByProfileId) {
        this.profileByProfileId = profileByProfileId;
    }

    public Shift getShiftByShiftId() {
        return shiftByShiftId;
    }

    public void setShiftByShiftId(Shift shiftByShiftId) {
        this.shiftByShiftId = shiftByShiftId;
    }
}
