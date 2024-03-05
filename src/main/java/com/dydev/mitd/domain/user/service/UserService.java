package com.dydev.mitd.domain.user.service;

import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDto getUserById(String userId);

    Page<UserResponseDto> getUserListWithSearchAndPaging(UserRequestDto.Search search, Pageable pageable);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    void updateUserWithSignIn(String userId, String refreshToken);

    void deleteUserById(String userId);

}
