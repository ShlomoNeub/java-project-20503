package com.example.demo.db.entities;


import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Users implements Serializable, IEntity<Users,Integer> {

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

    @Size(min = PASSWORD_MIN_LENGTH,max=PASSWORD_MAX_LENGTH)
    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String username;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users users)) return false;
        return getId().equals(
                users.getId()) &&
                getPid().equals(users.getPid()) &&
                getPassword().equals(users.getPassword()) &&
                getProfile().equals(users.getProfile()) &&
                getRoleId().equals(users.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPid(), getPassword(), getProfile(), getRole());
    }


    private boolean validatePassword(String password){
        boolean retVal = true;
        retVal &= password.length() > Users.PASSWORD_MIN_LENGTH;
        retVal &= password.length() <  Users.PASSWORD_MAX_LENGTH;
        return retVal;
    }

    @Override
    public boolean isValid(Users user) {
        boolean isValidPassword =validatePassword(user.getPassword());
        return isValidPassword;
    }

    @Override
    public int compareTo(Users o) {
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
