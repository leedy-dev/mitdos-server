package com.dydev.mitd.common.encode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncodeTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        passwordEncoder = new BCryptPasswordEncoder(10);
    }

    @Test
    @DisplayName("비밀번호 암호화 테스트")
    void encodeTest() {
        String encoded = passwordEncoder.encode("123");

        System.out.println("encoded password: " + encoded);

        Assertions.assertThat(passwordEncoder.matches("1234", encoded)).isTrue();
    }

}
