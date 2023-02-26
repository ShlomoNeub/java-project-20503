/**
 * Create by Shlomo Neuberger
 */
package com.project.db.dao;

import javax.ejb.Stateless;
import javax.persistence.*;
import com.project.db.entities.*;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Implementation of the user DataAccessObject
 */
@Stateless
public class UserDaoImp implements UserDao{
    private static final int MIN_PASSWORD_LENGTH =8;
    @PersistenceContext(unitName = "UsersPU")
    EntityManager entityManager;

    /**
     * Get all the user in the database
     * @return A List of users
     */
    @Override
    public List<User> getAll() {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_ALL, User.class)
                .getResultList();
    }

    /**
     * insert user to the database after validating the user
     * @param u to insert
     */
    @Override
    public void insert(User u) {
        if(validate(u))
            entityManager.persist(u);
    }

    /**
     * Updates a user in the database
     * @param u to update
     * @return newly change update or null
     */
    @Nullable
    @Override
    public User update(User u) {
        if(validate(u))return entityManager.merge(u);
        return null;
    }

    /**
     * delete a User form the database
     * @param u to be deleted
     */
    @Override
    public void delete(User u) {
        entityManager.remove(entityManager.merge(u));
    }

    /**
     * Validate that the password comforts to request
     * @return
     */
    private boolean validatePassword(String password){
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    /**
     * Validate the user object
     * @param u to validate
     * @return if the user is valid
     */
    @Override
    public boolean validate(User u) {
        // TODO: add more validation if needed
        return validatePassword(u.getPassword());
    }

    /**
     * Get user by id
     * @param id of the entry
     * @return the found User or null
     */
    @Nullable
    @Override
    public User getById(long id) {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_BY_ID,User.class)
                .setParameter("id",id)
                .getSingleResult();
    }



    /**
     * Gets the user profile
     * @param user whom profile will be retrieved
     * @return the retrieved profile
     */
    @Override
    public Profile getUserProfile(User user) {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_PROFILE_BY_ID,Profile.class)
                .setParameter("id",user.getUid())
                .getSingleResult();
    }
}
