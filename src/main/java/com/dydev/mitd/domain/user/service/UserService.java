package com.dydev.mitd.domain.user.service;

import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {

    User getUserEntityById(String userId);

    UserResponseDto getUserById(String userId);

    Page<UserResponseDto> getUserListWithSearchAndPaging(UserRequestDto.Search search, Pageable pageable);

    String createUser(UserRequestDto userRequestDto);

    String updateUserWithSignIn(String userId, String refreshToken);

    String updateUser(String userId, UserRequestDto.Update userRequestDto);

    String updateUserPassword(String userId, UserRequestDto.Password userRequestDto);

    String updateUserRoles(String userId, UserRequestDto.Role userRequestDto);

    String deleteUserById(String userId);

}
