package com.dydev.mitd.domain.user.service.impl;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.common.exception.ErrorMessage;
import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.repository.UserRepository;
import com.dydev.mitd.domain.user.service.UserService;
import com.dydev.mitd.domain.user.service.UserTokenService;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserTokenService userTokenService;

    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto getUserById(String userId) {
        Optional<User> userOp = userRepository.findById(userId);

        return modelMapper.map(
                userOp.orElseThrow(() -> new ApiException(userId, ErrorMessage.USER_NOT_FOUND)),
                UserResponseDto.class);
    }

    @Override
    public Page<UserResponseDto> getUserListWithSearchAndPaging(UserRequestDto.Search search, Pageable pageable) {
        Page<User> userList = userRepository.findUserListWithSearchAndPaging(search, pageable);

        return new PageImpl<>(userList.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList())
                , userList.getPageable()
                , userList.getTotalElements());
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        String userId = userRequestDto.getUserId();

        // 중복 체크
        Optional<User> userOp = userRepository.findById(userId);

        if (userOp.isPresent()) throw new ApiException(userId, ErrorMessage.DUPLICATE_USER_ID);

        User user = modelMapper.map(userRequestDto, User.class);

        return modelMapper.map(userRepository.save(user), UserResponseDto.class);
    }

    @Override
    @Transactional
    public void updateUserWithSignIn(String userId, String refreshToken) {
        Optional<User> userOp = userRepository.findById(userId);

        User user = userOp.orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));

        // 최종 로그인일시
        user.updateLastLoginDateTime();

        // refresh token 최신화
        userTokenService.updateUserToken(userId, refreshToken);
    }

    @Override
    @Transactional
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    /* user details */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findById(userId);

        return userOp.orElseThrow(() -> new ApiException(userId, ErrorMessage.USER_NOT_FOUND));
    }

}
