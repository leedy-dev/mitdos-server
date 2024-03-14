package com.dydev.mitd.domain.role.repository;

import com.dydev.mitd.domain.role.embedded.UserRoleId;
import com.dydev.mitd.domain.role.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
