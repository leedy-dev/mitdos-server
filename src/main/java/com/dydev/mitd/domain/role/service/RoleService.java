package com.dydev.mitd.domain.role.service;

import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;

public interface RoleService {

    Role getRoleEntityByRoleId(RoleTypes roleId);

}
