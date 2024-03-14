package com.dydev.mitd.domain.user.controller;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("User 조회 by id 테스트")
    void getUserByIdTest() throws Exception {
        // given
        String testId = "test01";

        // when, then
        mockMvc.perform(get(CommonApiUrls.API_PACKAGE_USER + "/{userId}", testId))
                .andExpect(status().isOk());
        verify(userService).getUserById(testId);
    }

}
