package com.dydev.mitd.domain.role.repository;

import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByRoleId(RoleTypes roleId);

}
