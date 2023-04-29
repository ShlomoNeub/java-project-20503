package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role implements Serializable, IEntity<Role, Integer> {
    // TODO: Think about making it Enum
    public static final int STANDARD_ROLE_LEVEL = 0;
    public static final int MANAGER_ROLE_LEVEL = 1;
    public static final int SUPER_ADMIN_ROLE_LEVEL = 2;

    public static final String STANDARD_ROLE_NAME = "STANDARD";
    public static final String MANAGER_ROLE_NAME = "MANAGER";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    private String roleName = STANDARD_ROLE_NAME;
    @Column(nullable = false)
    private Integer roleLevel = STANDARD_ROLE_LEVEL;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private Collection<User> users;

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

    @Nullable
    public Collection<User> getUsers() {
        return users;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }


    private boolean validateRoleInfo(String role_name, Integer role_level) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return getRoleName().equals(role.getRoleName()) && getRoleLevel().equals(role.getRoleLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleName(), getRoleLevel());
    }

    @Override
    public boolean isValid(Role toValidate) {
        return this.validateRoleInfo(toValidate.getRoleName(), toValidate.getRoleLevel());
    }

    @Override
    public int compareTo(@NonNull Role o) {
        try {
            return this.equals(o) ? 0 : this.id.compareTo(o.id);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleLevel='" + roleLevel + '\'' +
                '}';
    }
}
