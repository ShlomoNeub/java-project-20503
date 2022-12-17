/**
 * Create by Shlomo Neuberger
 */
package com.project.db.dao;

import com.project.db.entities.User;

import javax.ejb.Stateless;
import javax.persistence.*;
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
                .createNamedQuery(User.GET_ALL_QUERY, User.class)
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
                .createQuery("SELECT u FROM User u WHERE u.uid=: uid ORDER BY u.fkPid",User.class)
                .setParameter("uid",id)
                .getSingleResult();
    }


}
