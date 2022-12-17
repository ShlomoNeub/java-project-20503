package com.project.db.dao;

import com.project.db.entities.Profile;
import com.project.db.entities.User;
import com.sun.istack.internal.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserDao extends DAO<User> {

    @Nullable
    public User getById(@NotNull long id);

    public List<User> getByProfile(@NotNull Profile p);



}
