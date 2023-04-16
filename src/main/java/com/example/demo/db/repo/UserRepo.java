package com.example.demo.db.repo;
import com.example.demo.db.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface UserRepo extends CrudRepository<User,Integer>{
    User findFirstByUsernameAndPasswordOrderByUsernameAsc(String username, String password);

    @Query("FROM User u where u.pid= :pid")
    List<User> findByProfile(Integer pid);

    @Query("select u from User u LEFT join u.constraints constraints where constraints IS NULL OR not ((?1 BETWEEN constraints.startDate AND constraints.endDate) OR NOT (?2 BETWEEN constraints.startDate AND constraints.endDate) )")
    List<User> findUsersFreeAt(Date startDate,Date EndDate);




}
