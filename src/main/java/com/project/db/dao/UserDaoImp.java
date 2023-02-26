/**
 * Create by Shlomo Neuberger
 */
package com.project.db.dao;

import javax.ejb.Stateless;
import javax.persistence.*;
import com.project.db.entities.*;
import java.util.List;

/**
 * Implementation of the user DataAccessObject
 */
@Stateless
public class UserDaoImp implements UserDao{

    @PersistenceContext(unitName = "UsersPU")
    EntityManager entityManager;

    @Override
    public List<User> getAll() {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_ALL, User.class)
                .getResultList();
    }

    @Override
    public void insert(User u) {
        entityManager.persist(u);
    }

    @Override
    public User update(User u) {
        return entityManager.merge(u);
    }

    @Override
    public void delete(User u) {
        entityManager.remove(entityManager.merge(u));
    }

    @Override
    public User getById(long id) {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_BY_ID,User.class)
                .setParameter("id",id)
                .getSingleResult();
    }


    @Override
    public Profile getUserProfile(User user) {
        return entityManager
                .createNamedQuery(Queries.UserQueries.GET_PROFILE_BY_ID,Profile.class)
                .setParameter("id",user.getUid())
                .getSingleResult();
    }
}
