package com.dydev.mitd.domain.role.service;

import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;

import java.util.Set;

public interface RoleService {

    Role getRoleEntityByRoleId(RoleTypes roleId);

    Set<Role> getRoleEntityList(Set<RoleTypes> roleIdList);

}
