package com.dydev.mitd.common.utils;

import com.dydev.mitd.common.constants.AuthProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

public class CookieUtils {

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private static int refreshTokenValiditySeconds;

    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie getCookieValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static Cookie createTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie(AuthProperties.HEADER_PREFIX_RT, refreshToken);

        // 기간
        cookie.setMaxAge(refreshTokenValiditySeconds);

        // 설정
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

}
