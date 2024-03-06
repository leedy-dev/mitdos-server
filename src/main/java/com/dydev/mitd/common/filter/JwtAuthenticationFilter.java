package com.dydev.mitd.common.filter;

import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.common.utils.AESUtils;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.service.UserTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final UserTokenService userTokenService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthDto.SignIn authDto = modelMapper.map(request.getInputStream(), AuthDto.SignIn.class);

        // auth
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authDto.getUserId(), authDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        String userId = user.getUsername();

        // generate token
        AuthDto.TokenWithRefresh tokenDto = jwtProvider.generateToken(userId);

        // set header
        // access token
        jwtProvider.setAccessTokenHeader(tokenDto.getAccessToken(), response);
        // encrypt refresh token
        String refreshToken = tokenDto.getRefreshToken();
        String encryptedRefreshToken = AESUtils.encrypt(refreshToken);
        jwtProvider.setRefreshTokenHeader(encryptedRefreshToken, response);

        // update refresh token
        userTokenService.updateUserToken(userId, refreshToken);

        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
