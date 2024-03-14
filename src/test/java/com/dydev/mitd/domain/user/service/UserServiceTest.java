package com.dydev.mitd.domain.user.service;

import com.dydev.mitd.common.exception.exception.ApiException;
import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.repository.UserRepository;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import com.dydev.mitd.domain.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String TEST_USER_ID = "test01";

    private static final User TEST_USER = User.builder()
            .userId(TEST_USER_ID)
            .password("1234")
            .name("김테스트")
            .nickname("테스트왕")
            .email("test01@test.com")
            .build();

    @Test
    @DisplayName("User 조회 성공 테스트")
    void findUserByIdSuccessTest() {
        // given
        doReturn(Optional.of(TEST_USER)).when(userRepository).findById(TEST_USER_ID);

        // when
        User user = userService.getUserEntityById(TEST_USER_ID);

        // then
        assertThat(user.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(user.getName()).isEqualTo(TEST_USER.getName());
        assertThat(user.getNickname()).isEqualTo(TEST_USER.getNickname());
    }

    @Test
    @DisplayName("User 조회 실패 테스트")
    void findUserByIdFailTest() {
        // given
        doReturn(Optional.empty()).when(userRepository).findById(any());

        // when, then
        assertThatThrownBy(() -> userService.getUserEntityById(TEST_USER_ID)).isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("User 조회 Response 테스트")
    void findUserResponseById() {
        // given
        doReturn(Optional.of(TEST_USER)).when(userRepository).findById(any());

        // when
        userService.getUserById(TEST_USER_ID);

        // then
        verify(modelMapper, times(1)).map(any(User.class), eq(UserResponseDto.class));
    }

    @Test
    @DisplayName("User 생성 테스트")
    void createUserTest() {
        // given
        UserRequestDto userRequestDto = new UserRequestDto(TEST_USER);
        doReturn(TEST_USER).when(userRepository).save(any(User.class));

        // when
        String userId = userService.createUser(userRequestDto);

        // then
        assertThat(userId).isEqualTo(TEST_USER_ID);
    }

    @Test
    @DisplayName("User 수정 테스트")
    void updateUserTest() {
        // given
        UserRequestDto.Update userRequestDto = new UserRequestDto.Update(TEST_USER);
        doReturn(Optional.of(TEST_USER)).when(userRepository).findById(TEST_USER_ID);

        // when
        String userId = userService.updateUser(TEST_USER_ID, userRequestDto);

        // then
        assertThat(userId).isEqualTo(TEST_USER_ID);
    }

    @Test
    @DisplayName("User 삭제 테스트")
    void deleteUserByIdTest() {
        // given
        doReturn(Optional.of(TEST_USER)).when(userRepository).findById(TEST_USER_ID);

        // when
        userService.deleteUserById(TEST_USER_ID);

        // then
        verify(userRepository, times(1)).delete(any(User.class));
    }

}
