package com.project.db.services;

import com.project.db.dao.UserDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.project.db.entities.*;
import java.util.List;


@Stateless
public class UserServiceImpl implements UserService{
    @Inject
    UserDao dao;
    @Override
    public List<User> getAllUsers() {
        return dao.getAll();
    }

    @Override
    public User getUserById(User u) {
        return dao.getById(u.getId());
    }

    @Override
    public User getUserById(Long id) {
        return dao.getById(id);
    }

    @Override
    public User addUser(User u) {
        dao.insert(u);
        return u;
    }

    @Override
    public User updateUser(User u) {
        return dao.update(u);
    }

    @Override
    public void deleteUser(User u) {
        dao.delete(u);
    }

    @Override
    public void deleteUser(Long id) {
        User u  = dao.getById(id);
        if(u != null) dao.delete(u);
    }


}
