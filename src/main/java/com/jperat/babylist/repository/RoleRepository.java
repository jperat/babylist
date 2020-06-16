package com.jperat.babylist.repository;

import com.jperat.babylist.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findOneByRole(String role);
}
