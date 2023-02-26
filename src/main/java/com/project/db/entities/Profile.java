package com.project.db.entities;

import com.project.db.dao.Queries;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Queries.ProfileQueries.GET_ALL,query = "select p from Profile p")
})
public class Profile {
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "first_name", nullable = false, length = -1)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = -1)
    private String lastName;
    @Basic
    @Column(name = "phone_number", nullable = false, length = -1)
    private String phoneNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pid", nullable = false)
    private Integer pid;
    @OneToMany(mappedBy = "profileByPid")
    private Collection<User> usersByPid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(email, profile.email) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastName, profile.lastName) && Objects.equals(phoneNumber, profile.phoneNumber) && Objects.equals(pid, profile.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, phoneNumber, pid);
    }

    public Collection<User> getUsersByPid() {
        return usersByPid;
    }

    public void setUsersByPid(Collection<User> usersByPid) {
        this.usersByPid = usersByPid;
    }
}
