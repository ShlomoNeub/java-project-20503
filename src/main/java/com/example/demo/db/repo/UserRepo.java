package com.example.demo.db.repo;
import com.example.demo.db.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User,Integer>{
    User findFirstByUsernameAndPasswordOrderByUsernameAsc(String username, String password);

    @Query("FROM User u where u.pid= :pid")
    List<User> findByProfile(Integer pid);

}
