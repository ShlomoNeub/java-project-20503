package com.example.demo.db.repo;

import com.example.demo.db.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepo extends CrudRepository<Profile, Integer> {

}
