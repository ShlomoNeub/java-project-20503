package com.project.db.dao;

import com.project.db.entities.*;

public interface UserDao extends DAO<User> {
    /**
     * Gets the user profile
     * @param user
     * @return
     */
    public Profile getUserProfile(User user);





}
