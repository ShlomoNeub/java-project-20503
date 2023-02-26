package com.project.db.services;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(User u);
    public User getUserById(Long id);
    public  User addUser(User u);
    public User updateUser(User u);
    public void deleteUser (User u);
    public void deleteUser(Long id);

}
