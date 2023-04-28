package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.Objects;

/**
 * Entity to represent a job entry in the database
 */
@Entity
public class ScheduleJob implements IEntity<ScheduleJob, Integer> {

    /**
     * The id of the object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "uid", nullable = false)
    Integer userId;


    /**
     * Start date of the range that the Job will scan shift
     */
    @NotNull
    @Column(nullable = false)
    java.sql.Date startDate;

    /**
     * End date of the range that the Job will scan shift
     */
    @NotNull
    @Column(nullable = false)
    java.sql.Date endDate;

    /**
     * Is the job was done
     */
    Boolean done = false;


    @OneToOne
    @JoinColumn(
            name = "uid",
            referencedColumnName = "id",
            columnDefinition = "uid",
            insertable = false,
            updatable = false)
    @JsonBackReference
    User requester;


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
        boolean result = startDate != null;
        result &= endDate != null;
        result &= startDate.before(endDate) || startDate.equals(endDate);
        result &= userId != null;
        result &= userId >= 0;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleJob that)) return false;
        return Objects.equals(getId(), that.getId()) && getStartDate().equals(that.getStartDate()) && getEndDate().equals(that.getEndDate()) && Objects.equals(getDone(), that.getDone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartDate(), getEndDate(), getDone());
    }

    @Override
    public int compareTo(@NonNull ScheduleJob o) {
        if (this.equals(o)) return 0;
        if (!this.id.equals(o.id)) return this.id.compareTo(o.id);
        if (!this.startDate.equals(o.startDate)) return this.startDate.compareTo(o.startDate);
        if (!this.endDate.equals(o.endDate)) return this.endDate.compareTo(o.endDate);
        return 1;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getHash() {
        return this.hashCode();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
