package com.dydev.mitd.common.filter;

import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request header 에서 JWT 추출
        String token = jwtProvider.resolveAccessToken(request);

        // 토큰 유효성 검사
        if (CommonObjectUtils.nonNull(token) && jwtProvider.validateToken(token)) {
            // 유효할 경우 Authentication 객체를 SecurityContext에 저장
            Authentication authentication = jwtProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
