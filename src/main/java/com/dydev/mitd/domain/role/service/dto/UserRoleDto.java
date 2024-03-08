package com.dydev.mitd.domain.role.service.dto;

import com.dydev.mitd.domain.user.service.dto.UserDto;
import lombok.Getter;

@Getter
public class UserRoleDto {

    private UserDto userDto;

    private RoleDto roleDto;

}
