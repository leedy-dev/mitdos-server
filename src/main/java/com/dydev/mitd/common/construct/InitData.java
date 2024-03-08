package com.dydev.mitd.common.construct;

import com.dydev.mitd.common.constants.CommonConstants;
import com.dydev.mitd.domain.role.entity.Role;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.role.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitData {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initData() {
        // Role
        if (!roleRepository.existsByRoleId(RoleTypes.ROLE_USER)) {
            roleRepository.saveAll(
                    Arrays.stream(RoleTypes.values())
                            .map(r -> Role.builder()
                                    .roleId(r)
                                    .roleName(r.getValue())
                                    .createUserId(CommonConstants.SYSTEM)
                                    .build())
                            .collect(Collectors.toList()));
        }
    }

}
