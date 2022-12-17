package com.project.db.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profiles", schema = "public", catalog = "project")
@NamedQueries({
        @NamedQuery(name = Profile.GET_ALL_QUERY,query = "SELECT p FROM Profile p ORDER BY p.pid"),
//        @NamedQuery(name = Profile.GET_BY_ID_QUERY, query = "SELECT p FROM Profile p WHERE p.pid=: pid ORDER BY p.pid")
})
public class Profile {
    public static final String GET_ALL_QUERY = "Profile.findAll";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pid", nullable = false)
    private Long pid;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "f_name", nullable = false, length = -1)
    private String firstname;
    @Basic
    @Column(name = "l_name", nullable = false, length = -1)
    private String lastname;
    @OneToMany(mappedBy = "profilesByPid")
    private Collection<User> usersByPid;

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

    public Collection<User> getUsersByPid() {
        return usersByPid;
    }

    public void setUsersByPid(Collection<User> usersByPid) {
        this.usersByPid = usersByPid;
    }
}
