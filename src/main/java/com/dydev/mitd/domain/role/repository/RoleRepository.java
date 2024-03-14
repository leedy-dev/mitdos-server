package com.dydev.mitd.domain.role.repository;

import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByRoleId(RoleTypes roleId);

    @Query(value = "select r from Role r where r.roleId in (:#{#roleIdList})")
    Set<Role> findAllByRoleIdList(@Param("roleIdList") Set<RoleTypes> roleIdList);

}
