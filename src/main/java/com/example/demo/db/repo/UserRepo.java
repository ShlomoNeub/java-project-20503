package com.example.demo.db.repo;
import com.example.demo.db.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<Users,Integer>{
    Users findFirstByUsernameAndPasswordOrderByUsernameAsc(String username, String password);

    @Query("FROM Users u where u.pid= :pid")
    List<Users> findByProfile(Integer pid);

}
