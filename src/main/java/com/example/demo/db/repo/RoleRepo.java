package com.example.demo.db.repo;

import com.example.demo.db.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<Role, Integer> {

    /**
     * Get role that have the same level given
     *
     * @param roleLevel to search for
     * @return a role  {@code if } found {@code   else null}
     */
    @Query("select r from Role r where r.roleLevel = ?1 order by r.id")
    Optional<Role> getRoleByLevel(@NonNull Integer roleLevel);


}
