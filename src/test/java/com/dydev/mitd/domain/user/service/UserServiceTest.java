package com.dydev.mitd.domain.user.service;

import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.enums.UserTypes;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private static final String TEST_USER_ID = "test01";
    private static final User TEST_USER = User.builder()
            .userId(TEST_USER_ID)
            .password("1234")
            .dtype(UserTypes.USER.getValue())
            .name("김테스트")
            .email("test01@test.com")
            .build();

    @AfterEach
    void afterEach() {
        userService.deleteUserById(TEST_USER_ID);
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void 유저_생성_테스트() {
        UserRequestDto.Join userRequestDto = modelMapper.map(TEST_USER, UserRequestDto.Join.class);

        UserResponseDto userResponseDto = userService.createUser(userRequestDto);

        String responseUserId = userResponseDto.getUserId();

        assertThat(responseUserId).isEqualTo(TEST_USER_ID);
        assertThat(userService.getUserById(responseUserId)).isNotNull();
    }

}
