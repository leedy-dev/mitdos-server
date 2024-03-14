package com.dydev.mitd.domain.role.service.impl;

import com.dydev.mitd.domain.role.entity.UserRole;
import com.dydev.mitd.domain.role.repository.UserRoleRepository;
import com.dydev.mitd.domain.role.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

}
