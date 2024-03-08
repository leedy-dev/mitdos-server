package com.dydev.mitd.domain.role.service.dto;

import com.dydev.mitd.domain.role.enums.RoleTypes;
import lombok.Getter;

@Getter
public class RoleDto {

    private RoleTypes roleId;

    private String roleName;

}
