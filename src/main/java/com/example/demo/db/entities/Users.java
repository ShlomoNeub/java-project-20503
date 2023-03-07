package com.example.demo.db.entities;

import com.example.demo.db.Validatable;
import com.example.demo.db.repo.ProfileRepo;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Entity
public class Users implements Serializable {

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "pid",insertable = false,updatable = false)
    private Integer pid;

    @Size(min = 8,max=16)
    private String password;

    @ManyToOne
    @JoinColumn(name = "pid",referencedColumnName = "id", columnDefinition = "pid")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    Role role;


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
