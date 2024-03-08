package com.dydev.mitd.domain.role.service.impl;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.common.exception.ErrorMessage;
import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.role.repository.RoleRepository;
import com.dydev.mitd.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleEntityByRoleId(RoleTypes roleId) {
        return roleRepository.findById(roleId.name())
                .orElseThrow(() -> new ApiException(roleId.name(), ErrorMessage.DATA_NOT_FOUND));
    }

}
