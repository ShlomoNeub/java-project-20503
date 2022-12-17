package com.project.db.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NamedQueries({

})
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Basic
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "pid")
    private Long pid;
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)
    private Profile profileByPid;

    public User() {
    }

    public User(String username, String password, Profile profileByPid) {
        this.username = username;
        this.password = password;
        this.profileByPid = profileByPid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(pid, user.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, password, pid);
    }

    public Profile getProfilesByPid() {
        return profileByPid;
    }

    public void setProfilesByPid(Profile profileByPid) {
        this.profileByPid = profileByPid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pid=" + pid +
                ", profileByPid=" + profileByPid +
                '}';
    }
}
