package com.dydev.mitd.domain.role.service;

import com.dydev.mitd.domain.role.repository.RoleRepository;
import com.dydev.mitd.domain.role.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

}
