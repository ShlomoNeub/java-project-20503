package com.project.db.entities;


import com.project.db.dao.Queries;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Queries.ProfileQueries.GET_ALL, query = "SELECT p FROM Profile p"),
})
@Table(schema = "public")
public class Profile {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pid", nullable = false)
    private Integer pid;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    @Basic
    @Column(name = "f_name", nullable = false, length = -1)
    private String firstname;
    @Basic
    @Column(name = "l_name", nullable = false, length = -1)
    private String lastname;
    @OneToMany(mappedBy = "profileByProfileId")
    private Collection<User> usersByPid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(pid, profile.pid) && Objects.equals(email, profile.email) && Objects.equals(firstname, profile.firstname) && Objects.equals(lastname, profile.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, email, firstname, lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonbTransient
    public Collection<User> getUsersByPid() {
        return usersByPid;
    }

    public void setUsersByPid(Collection<User> usersByPid) {
        this.usersByPid = usersByPid;
    }
}
