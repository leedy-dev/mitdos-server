package com.dydev.mitd.common.utils;

import com.dydev.mitd.common.constants.AuthProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieUtils {

    public static final int refreshTokenValiditySeconds = 86400;

    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie getCookieValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (CommonObjectUtils.isNotEmpty(cookies)) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    public static Cookie createTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie(AuthProperties.HEADER_PREFIX_RT, refreshToken);

        // 기간
        cookie.setMaxAge(refreshTokenValiditySeconds);

        // 설정
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");

        return cookie;
    }

    public static Cookie removeTokenCookie() {
        Cookie cookie = new Cookie(AuthProperties.HEADER_PREFIX_RT, null);

        cookie.setMaxAge(0);
        cookie.setPath("/");

        return cookie;
    }

}
