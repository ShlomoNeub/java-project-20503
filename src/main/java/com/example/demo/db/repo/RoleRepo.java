package com.example.demo.db.repo;

import com.example.demo.db.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepo extends CrudRepository<Role,Integer> {

    /**
     * Gets all roles with role_level same to level
     * @param level to search for
     * @return List of roles with role_level equal to level
     */
    Collection<Role> getRolesByRoleLevel(Integer level);

    /**
     * Gets all roles with role_name same as name
     * @param name to search for
     * @return List of roles with role_name == name
     */
    Collection<Role> getRolesByRoleName(String name);

    /**
     * Get role that have the same level given
     * @param roleLevel to search for
     * @return a role  {@code if } found {@code   else null}
     */
    @Query("select r from Role r where r.roleLevel = ?1 order by r.id")
    Optional<Role> getRoleByLevel(@NonNull Integer roleLevel);




}
