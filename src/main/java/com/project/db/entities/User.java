package com.project.db.entities;

import com.project.db.dao.Queries;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Queries.UserQueries.GET_ALL ,query = "SELECT u FROM User u JOIN FETCH u.profileByProfileId"),
        @NamedQuery(name = Queries.UserQueries.GET_PROFILE_BY_ID, query = "SELECT p FROM Profile p where p.pid = :id"),
        @NamedQuery(name = Queries.UserQueries.GET_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id ORDER BY u.id"),
})
@Table(name = "user",schema = "public")

public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "pid", nullable = false)
    private Profile profileByProfileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

//    public Integer getProfileId() {
//        return profileId;
//    }
//
//    public void setProfileId(Integer profileId) {
//        this.profileId = profileId;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
    public Profile getProfileByProfileId() {
        return profileByProfileId;
    }

    public void setProfileByProfileId(Profile profileByProfileId) {
        this.profileByProfileId = profileByProfileId;
    }
}
