package com.dydev.mitd.domain.auth.service;

import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;

public interface AuthService {

    AuthDto.Token signIn(AuthDto.SignIn authDto);

    void signOut(String encryptedRefreshToken, String accessToken);

    void signUp(UserRequestDto.Join userRequestDto);

    String reissueAccessToken(String encryptedRefreshToken);

}
