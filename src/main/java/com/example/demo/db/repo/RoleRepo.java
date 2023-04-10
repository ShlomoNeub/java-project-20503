package com.example.demo.db.repo;

import com.example.demo.db.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepo extends CrudRepository<Role,Integer> {

    /**
     * Gets all roles with role_level same to level
     * @param level to search for
     * @return List of roles with role_level equal to level
     */
    List<Role> getRolesByRoleLevel(Integer level);

    /**
     * Gets all roles with role_name same as name
     * @param name to search for
     * @return List of roles with role_name == name
     */
    List<Role> getRolesByRoleName(String name);


}
