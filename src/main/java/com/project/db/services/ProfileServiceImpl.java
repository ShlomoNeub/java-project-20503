package com.project.db.services;

import com.project.db.dao.ProfileDao;
import com.project.db.entities.Profile;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ProfileServiceImpl implements ProfileService{
    @Inject
    ProfileDao dao;
    @Override
    public List<Profile> getAllProfiles() {
        return dao.getAll();
    }

    @Override
    public Profile getProfileById(Profile u) {
        return dao.getById(u.getPid());
    }

    @Override
    public Profile getProfileById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Profile AddProfile(Profile u) {
        dao.insert(u);
        return u;
    }

    @Override
    public Profile updateProfile(Profile u) {
        return dao.update(u);
    }

    @Override
    public void deleteProfile(Profile u) {
        dao.delete(u);
    }

    @Override
    public void deleteProfile(Long id) {
        Profile p  = dao.getById(id);
        dao.delete(p);

    }
}
