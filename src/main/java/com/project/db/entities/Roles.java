package com.project.db.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Roles {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @Basic
    @Column(name = "role_name", nullable = false, length = -1)
    private String roleName;
    @Basic
    @Column(name = "role_level", nullable = false)
    private Integer roleLevel;
    @OneToMany(mappedBy = "rolesByRoleId")
    private Collection<User> usersByRoleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(roleId, roles.roleId) && Objects.equals(roleName, roles.roleName) && Objects.equals(roleLevel, roles.roleLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, roleLevel);
    }

    public Collection<User> getUsersByRoleId() {
        return usersByRoleId;
    }

    public void setUsersByRoleId(Collection<User> usersByRoleId) {
        this.usersByRoleId = usersByRoleId;
    }
}
