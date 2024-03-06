package com.dydev.mitd.domain.auth.service.impl;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.common.exception.ErrorMessage;
import com.dydev.mitd.domain.auth.service.AuthService;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.common.utils.AESUtils;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import com.dydev.mitd.domain.user.entity.UserToken;
import com.dydev.mitd.domain.user.service.UserService;
import com.dydev.mitd.domain.user.service.UserTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final UserTokenService userTokenService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AuthDto.TokenWithRefresh signIn(AuthDto.SignIn authDto) {
        String userId = authDto.getUserId();

        // 토큰 생성
        AuthDto.TokenWithRefresh tokenDto = jwtProvider.generateToken(userId);

        // 사용자 정보 업데이트
        userService.updateUserWithSignIn(userId, tokenDto.getRefreshToken());

        return tokenDto;
    }

    @Override
    @Transactional
    public void signOut(String accessToken) {
        if (CommonObjectUtils.isNull(accessToken)) {
            throw new ApiException(ErrorMessage.ACCESS_TOKEN_NOT_FOUND);
        }

        // get user id
        Claims claims = jwtProvider.parseClaims(accessToken);

        String userId = claims.getSubject();

        // delete token
        userTokenService.deleteUserTokenByUserId(userId);

        // TODO: add token to black list
    }

    @Override
    @Transactional
    public void signUp(UserRequestDto.Join userRequestDto) {
        userService.createUser(userRequestDto);
    }

    @Override
    public String reissueAccessToken(String encryptedRefreshToken) {
        if (CommonObjectUtils.isNull(encryptedRefreshToken)) {
            throw new ApiException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND);
        }

        // decrypt refresh token
        String refreshToken = AESUtils.decrypt(encryptedRefreshToken);

        // get claims
        Claims claims = jwtProvider.parseClaims(refreshToken);

        String userId = claims.getSubject();

        // get refresh token by user id
        Optional<UserToken> userTokenOp = userTokenService.getUserTokenOpByUserId(userId);

        // exist token && equals token
        if (userTokenOp.isPresent() && userTokenOp.get().getRefreshToken().equals(refreshToken)) {
            // check exists user by id
            userService.getUserById(userId);

            // generate new access token
            return jwtProvider.generateToken(userId).getAccessToken();
        }

        // invalid token
        else {
            throw new ApiException(ErrorMessage.INVALID_REFRESH_TOKEN);
        }
    }

}
