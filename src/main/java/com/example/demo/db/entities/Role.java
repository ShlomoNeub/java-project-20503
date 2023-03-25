package com.example.demo.db.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role implements Serializable {
    // TODO: Think about making it Enum
    public static final int STANDARD_ROLE_LEVEL = 1;
    public static final int MANAGER_ROLE_LEVEL = 2;

    public static final String STANDARD_ROLE_NAME = "STANDARD";
    public static final String MANAGER_ROLE_NAME = "MANAGER";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    private Integer roleLevel;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    @Nullable
    private Collection<Users> users;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }


    private boolean validateRoleInfo(String role_name,Integer role_level){
        boolean retVal = false;
        boolean[] atLeastOne =
                { // here we put all the conditions where we need at least one to be true
                        role_level == STANDARD_ROLE_LEVEL && Objects.equals(role_name, STANDARD_ROLE_NAME),
                        role_level == MANAGER_ROLE_LEVEL && Objects.equals(role_name, MANAGER_ROLE_NAME)
                };
        for (boolean b : atLeastOne) {
            retVal |= b;
        }


        return retVal;
    }


    public boolean isValid() {
        return  this.validateRoleInfo(roleName, roleLevel);
    }
}
