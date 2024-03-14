package com.dydev.mitd.domain.auth.service;

import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;

public interface AuthService {

    AuthDto.TokenWithRefresh signIn(AuthDto.SignIn authDto);

    void signOut(String accessToken);

    String signUp(UserRequestDto.Join userRequestDto);

    String reissueAccessToken(String encryptedRefreshToken);

}
