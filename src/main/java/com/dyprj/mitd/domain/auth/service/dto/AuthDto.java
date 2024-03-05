package com.dyprj.mitd.domain.auth.service.dto;

import com.dyprj.mitd.common.constants.AuthProperties;
import com.dyprj.mitd.common.constants.CommonConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

public class AuthDto {

    @Getter
    public static class SignIn {
        @NotEmpty
        private String userId;

        @NotEmpty
        private String password;
    }

    @Getter
    @Builder
    public static class Token {
        @Builder.Default
        private String grantType = AuthProperties.GRANT_TYPE;

        private String accessToken;
        private String refreshToken;
    }

}
