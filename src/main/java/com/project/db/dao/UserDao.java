package com.project.db.dao;

import com.project.db.entities.*;

public interface UserDao extends DAO<User> {

    public Profile getUserProfile(User user);





}
