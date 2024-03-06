package com.dydev.mitd.domain.auth.service.dto;

import com.dydev.mitd.common.constants.AuthProperties;
import com.dydev.mitd.common.utils.AESUtils;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

public class AuthDto {

    @Getter
    public static class SignIn {
        @NotEmpty
        private String userId;

        @NotEmpty
        private String password;
    }

    @Getter
    @SuperBuilder
    public static class Token {
        @Builder.Default
        private String grantType = AuthProperties.GRANT_TYPE;

        private String accessToken;
    }

    @Getter
    @SuperBuilder
    public static class TokenWithRefresh extends Token {
        private String refreshToken;

        public String encryptRefreshToken() {
            return AESUtils.encrypt(refreshToken);
        }
    }

}
