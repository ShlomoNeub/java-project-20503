package com.example.demo.db.repo;

import com.example.demo.db.entities.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepo extends CrudRepository<Profile,Integer> {

}
