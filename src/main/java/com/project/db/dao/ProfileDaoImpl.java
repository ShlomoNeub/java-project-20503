package com.project.db.dao;

import com.project.db.entities.Profile;
import com.project.db.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProfileDaoImpl implements ProfileDao {
    @PersistenceContext(unitName = "UsersPU")
    EntityManager entityManager;
    @Override
    public List<Profile> getAll() {
        return entityManager
                .createNamedQuery(Profile.GET_ALL_QUERY,Profile.class)
                .getResultList();
    }

    @Override
    public void insert(Profile u) {
        entityManager.persist(u);
    }

    @Override
    public Profile update(Profile u) {
        return entityManager.merge(u);
    }

    @Override
    public void delete(Profile u) {
        entityManager.remove(entityManager.merge(u));
    }

    @Override
    public Profile getById(long id) {
        return entityManager
                .createQuery("SELECT p FROM Profile p WHERE p.pid=: pid ORDER BY p.pid",Profile.class)
                .setParameter("pid",id)
                .getSingleResult();
    }
}
