package com.project.db.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "project")
@NamedQueries({
        @NamedQuery(name = User.GET_ALL_QUERY, query = "SELECT u FROM User u ORDER BY u.fkPid")
})
public class User {
    public final static String GET_ALL_QUERY = "Users.findAll";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @Basic
    @Column(name = "password", nullable = true, length = -1)
    private String password;
    @Basic
    @Column(name = "pid", nullable = false)
    private Long fkPid;
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false,insertable=false, updatable=false)
    private Profile profilesByPid;

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

    public Long getFkPid() {
        return fkPid;
    }

    public void setFkPid(Long fkPid) {
        this.fkPid = fkPid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(fkPid, user.fkPid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, password, fkPid);
    }

    public Profile getProfilesByPid() {
        return profilesByPid;
    }

    public void setProfilesByPid(Profile profilesByPid) {
        this.profilesByPid = profilesByPid;
    }
}
