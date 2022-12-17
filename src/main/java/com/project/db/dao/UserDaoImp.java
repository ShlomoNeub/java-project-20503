/**
 * Create by Shlomo Neuberger
 */
package com.project.db.dao;

import com.project.db.entities.Profile;
import com.project.db.entities.User;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Implementation of the service
 */
@Stateless
public class UserDaoImp implements UserDao{

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void insert(User u) {

    }

    @Override
    public void update(User u) {

    }

    @Override
    public void delete(User u) {

    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public List<User> getByProfile(Profile p) {
        return null;
    }
}
