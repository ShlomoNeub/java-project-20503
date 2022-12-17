package com.project.db.services;

import com.project.db.entities.Profile;
import com.project.db.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProfileService {
    public List<Profile> getAllProfiles();
    public Profile getProfileById(Profile u);
    public Profile getProfileById(Long id);
    public  Profile AddProfile(Profile u);
    public Profile updateProfile(Profile u);
    public void deleteProfile (Profile u);
    public void deleteProfile(Long id);
}
