package com.project.db.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class User {
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid", nullable = false)
    private Integer uid;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "pid", nullable = false)
    private Integer pid;
    @Basic
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @OneToMany(mappedBy = "userByUid")
    private Collection<ShiftsRequests> shiftsRequestsByUid;
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)
    private Profile profileByPid;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private Roles rolesByRoleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(uid, user.uid) && Objects.equals(password, user.password) && Objects.equals(pid, user.pid) && Objects.equals(roleId, user.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, uid, password, pid, roleId);
    }

    public Collection<ShiftsRequests> getShiftsRequestsByUid() {
        return shiftsRequestsByUid;
    }

    public void setShiftsRequestsByUid(Collection<ShiftsRequests> shiftsRequestsByUid) {
        this.shiftsRequestsByUid = shiftsRequestsByUid;
    }

    public Profile getProfileByPid() {
        return profileByPid;
    }

    public void setProfileByPid(Profile profileByPid) {
        this.profileByPid = profileByPid;
    }

    public Roles getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(Roles rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }
}
