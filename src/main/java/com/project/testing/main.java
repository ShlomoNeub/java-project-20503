package com.project.testing;

import com.project.db.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UsersPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User u = new User();
        u.setUsername ("Shlomo");
        u.setPassword("s@example.com");
        em.persist(u);


        tx.commit();
        em.close();
    }
}
