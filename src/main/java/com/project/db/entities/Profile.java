package com.project.db.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profiles")
public class Profile {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pid")
    private Long pid;
    @Basic
    @Column(name = "email")
    @Size(max = 255)
    private String email;
    @Basic
    @Column(name = "f_name")
    @Size(max = 255)
    private String firstName;
    @Basic
    @Column(name = "l_name")
    @Size(max = 255)
    private String lastname;
    @OneToMany(mappedBy = "profileByPid")
    private Collection<User> userByPid;

    public Profile() {
    }

    public Profile(String email, String firstName, String lastname) {
        this.email = email;
        this.firstName = firstName;
        this.lastname = lastname;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(pid, profile.pid) && Objects.equals(email, profile.email) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastname, profile.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, email, firstName, lastname);
    }

    public Collection<User> getUsersByPid() {
        return userByPid;
    }

    public void setUsersByPid(Collection<User> userByPid) {
        this.userByPid = userByPid;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "pid=" + pid +
                ", email='" + email + '\'' +
                ", fName='" + firstName + '\'' +
                ", lName='" + lastname + '\'' +
                '}';
    }
}
