package com.project.db.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.project.db.entities.*;
import java.util.List;
import java.util.regex.Pattern;


@Stateless
public class ProfileDaoImpl implements ProfileDao {
    @PersistenceContext(unitName = "UsersPU")
    EntityManager entityManager;

    /**
     * Get all profiles for the database
     * @return List of all the profiles
     */
    @Override
    public List<Profile> getAll() {
        return entityManager
                .createNamedQuery(Queries.ProfileQueries.GET_ALL,Profile.class)
                .getResultList();
    }

    /**
     * Insert single profile after validation
     * @param u to insert
     */
    @Override
    public void insert(Profile u) {
        if(validate(u))  entityManager.persist(u);
    }

    /**
     * Update a given profile if valid null if not
     * @param u to update
     * @return the profile or null if unable to update
     */
    @Override
    public Profile update(Profile u) {
        if(validate(u)) return entityManager.merge(u);
        else return null;
    }

    /**
     * Delete a profile
     * @param u to be deleted
     */
    @Override
    public void delete(Profile u) {
        entityManager.remove(entityManager.merge(u));
    }

    /**
     * Validates if the given string is valid email address
     * @param emailAddress to validate
     * @return if the string is valid email
     */
    private boolean validateEmail(String emailAddress){
        // TODO: Create email validation regex
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return  Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    /**
     * Validate if the given profile is valid
     * @param u to validate
     * @return if the profile is valid
     */
    @Override
    public boolean validate(Profile u) {
        // TODO: Add more validation if needed
        return validateEmail(u.getEmail());
    }

    /**
     * Retrive a spesific profile by its id
     * @param id of the entry
     * @return the profile if exists else null
     */
    @Override
    public Profile getById(long id) {
        return entityManager
                .createQuery("SELECT p FROM Profile p WHERE p.pid=: pid ORDER BY p.pid",Profile.class)
                .setParameter("pid",id)
                .getSingleResult();
    }
}
