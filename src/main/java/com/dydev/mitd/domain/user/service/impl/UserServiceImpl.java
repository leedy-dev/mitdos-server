package com.dydev.mitd.domain.user.service.impl;

import com.dydev.mitd.common.exception.exception.ApiException;
import com.dydev.mitd.common.exception.message.ErrorMessage;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.domain.role.embedded.UserRoleId;
import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.entity.UserRole;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.role.service.RoleService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserTokenService userTokenService;

    private final ModelMapper modelMapper;

    @Override
    public User getUserEntityById(String userId) {
        Optional<User> userOp = userRepository.findById(userId);
        return userOp.orElseThrow(() -> new ApiException(userId, ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        return modelMapper.map(getUserEntityById(userId), UserResponseDto.class);
    }

    @Override
    public Page<UserResponseDto> getUserListWithSearchAndPaging(UserRequestDto.Search search, Pageable pageable) {
        Page<User> userList = userRepository.findUserListWithSearchAndPaging(search, pageable);

        return new PageImpl<>(userList.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList()),
                userList.getPageable(),
                userList.getTotalElements());
    }

    @Override
    @Transactional
    public String createUser(UserRequestDto userRequestDto) {
        String userId = userRequestDto.getUserId();

        // 중복 체크 id
        if (userRepository.existsById(userId)) {
            throw new ApiException(userId, ErrorMessage.DUPLICATE_USER_ID);
        }

        // 중복 체크 email
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new ApiException(userRequestDto.getEmail(), ErrorMessage.DUPLICATE_EMAIL);
        }

        // build user
        User user = User.builder()
                // user info
                .userId(userRequestDto.getUserId())
                .password(userRequestDto.getPassword())
                .name(userRequestDto.getName())
                .nickname(userRequestDto.getNickname())
                .email(userRequestDto.getEmail())

                // credential
                .accountExpired(false)
                .credentialExpired(false)
                .locked(false)
                .enabled(true)

                .build();

        // get default role
        Role defaultRole = Role.getDefaultRole();

        // add role
        user.applyRoles(Set.of(
                UserRole.builder()
                        .userRoleId(UserRoleId.builder()
                                .userId(userId)
                                .roleId(defaultRole.getRoleId())
                                .build())
                        .user(user)
                        .role(defaultRole)
                        .build()
        ));

        // save
        user = userRepository.save(user);

        return user.getUserId();
    }

    @Override
    @Transactional
    public String updateUserWithSignIn(String userId, String refreshToken) {
        Optional<User> userOp = userRepository.findById(userId);

        User user = userOp.orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));

        // 최종 로그인일시
        user.updateLastLoginDateTime();

        // refresh token 최신화
        userTokenService.updateUserToken(userId, refreshToken);

        return userId;
    }

    @Override
    @Transactional
    public String updateUser(String userId, UserRequestDto.Update userRequestDto) {
        // get
        User user = getUserEntityById(userId);

        // update
        modelMapper.map(userRequestDto, user);

        return user.getUserId();
    }

    @Override
    @Transactional
    public String updateUserPassword(String userId, UserRequestDto.Password userRequestDto) {
        // get
        User user = getUserEntityById(userId);

        // update
        user.updatePassword(userRequestDto.getPassword());

        return user.getUserId();
    }

    @Override
    @Transactional
    public String updateUserRoles(String userId, UserRequestDto.Role userRequestDto) {
        // get user
        User user = getUserEntityById(userId);

        // request roles
        Set<RoleTypes> requestRoleTypes = userRequestDto.getRoles();
        
        // original roles
        Set<UserRole> originalRoles = user.getUserRoles();

        // get roles
        Set<Role> roles = roleService.getRoleEntityList(userRequestDto.getRoles());

        // remove roles
        originalRoles.forEach(ur -> {
            if (CommonObjectUtils.nonNull(ur.getId()) && !requestRoleTypes.contains(ur.getId().getRoleId())) {
                user.removeRole(ur);
            }
        });

        // add roles
        user.addRoles(roles.stream()
                .filter(r -> !originalRoles.stream()
                        .map(or -> CommonObjectUtils.nonNull(or.getId()) ? or.getId().getRoleId() : null)
                        .collect(Collectors.toSet())
                        .contains(r.getRoleId()))
                .map(r -> {
                    UserRole userRole = UserRole.builder()
                            .userRoleId(UserRoleId.builder()
                                    .roleId(r.getRoleId())
                                    .userId(user.getUserId())
                                    .build())
                            .build();

                    userRole.applyUser(user);
                    userRole.applyRole(r);

                    return userRole;
                })
                .collect(Collectors.toSet())
        );

        return user.getUserId();
    }

    @Override
    @Transactional
    public String deleteUserById(String userId) {
        // get
        User user = getUserEntityById(userId);

        // delete
        userRepository.delete(user);

        return user.getUserId();
    }

    /* user details */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findById(userId);

        return userOp.orElseThrow(() -> new ApiException(userId, ErrorMessage.USER_NOT_FOUND));
    }

}
