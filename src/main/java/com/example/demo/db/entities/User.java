/**
 * This file implements the User entities that login credentials
 */
package com.example.demo.db.entities;


import com.example.demo.config.annotation.ExcludeGson;
import com.example.demo.config.annotation.PrivateGson;
import com.example.demo.db.entities.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
public class User implements Serializable, IEntity<User,Integer> {

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "pid",nullable = false)
    private Integer pid;
    @Basic
    @Column(name = "role_id",nullable = false)
    private Integer roleId;

    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = PASSWORD_MIN_LENGTH,max=PASSWORD_MAX_LENGTH)
    @Column(nullable = false)
    @PrivateGson
    @ExcludeGson
    private String password;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    @Nullable
    private Collection<Constraint> constraints;

    @ManyToOne
    @JoinColumn(name = "pid",
            referencedColumnName = "id",
            columnDefinition = "pid",
            insertable = false,updatable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "role_id",
            referencedColumnName = "id",
            columnDefinition="role_id",
            insertable = false,
            updatable = false)
    Role role;


    @OneToMany(mappedBy = "user")
    List<ShiftsRequests> requests;


    public User() {
    }

    public User(Integer pid, Integer roleId, String password, String username) {
        this.pid = pid;
        this.roleId = roleId;
        this.password = password;
        this.username = username;
    }

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

    public void setPid(Integer pid){
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId().equals(
                user.getId()) &&
                getPid().equals(user.getPid()) &&
                getPassword().equals(user.getPassword()) &&
                getProfile().equals(user.getProfile()) &&
                getRoleId().equals(user.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPid(), getPassword(), getProfile(), getRole());
    }


    private boolean validatePassword(String password){
        boolean retVal = true;
        retVal &= password.length() > User.PASSWORD_MIN_LENGTH;
        retVal &= password.length() <  User.PASSWORD_MAX_LENGTH;
        return retVal;
    }

    @Override
    public boolean isValid(User user) {
        return validatePassword(user.getPassword());
    }

    @Override
    public int compareTo(@NonNull User o) {
        if (o != null) {
            try{
                return equals(o)?0:this.getId().compareTo(o.getId());
            }catch (Exception e){
                return 1;
            }
        }
        else return 1;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRole_id(Integer roleId) {
        this.roleId = roleId;
    }
    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", pid=" + pid +
                ", roleId=" + roleId +
                '}';
    }



}
