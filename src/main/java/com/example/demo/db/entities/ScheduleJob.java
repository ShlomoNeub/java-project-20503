package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.Objects;

/**
 * Entity the represent a job entry in the database
 */
@Entity
public class ScheduleJob implements IEntity<ScheduleJob, Integer> {

    /**
     * The id of the object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * Start date of the range that the Job will scan shift
     */
    @NotNull
    java.sql.Date startDate;

    /**
     * End date of the range that the Job will scan shift
     */
    @NotNull
    java.sql.Date endDate;

    /**
     * Is the job was done
     */
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
        if(this.equals(o)) return 0;
        if(!this.id.equals(o.id)) return this.id.compareTo(o.id);
        if(!this.startDate.equals(o.startDate)) return this.startDate.compareTo(o.startDate);
        if(!this.endDate.equals(o.endDate)) return this.endDate.compareTo(o.endDate);
        return 1;
    }
}
