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
        String encoded1 = passwordEncoder.encode("123");
        String encoded2 = passwordEncoder.encode("123");
        String encoded3 = passwordEncoder.encode("123");

        System.out.println("encoded password 1: " + encoded1);
        System.out.println("encoded password 2: " + encoded2);
        System.out.println("encoded password 3: " + encoded3);

        Assertions.assertThat(passwordEncoder.matches("123", encoded1)).isTrue();
        Assertions.assertThat(passwordEncoder.matches("123", encoded2)).isTrue();
        Assertions.assertThat(passwordEncoder.matches("123", encoded3)).isTrue();
    }

}
