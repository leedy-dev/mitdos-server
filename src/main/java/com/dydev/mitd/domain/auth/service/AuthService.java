package com.dydev.mitd.domain.auth.service;

import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;

public interface AuthService {

    AuthDto.TokenWithRefresh signIn(AuthDto.SignIn authDto);

    void signOut(String accessToken);

    void signUp(UserRequestDto.Join userRequestDto);

    String reissueAccessToken(String encryptedRefreshToken);

}
