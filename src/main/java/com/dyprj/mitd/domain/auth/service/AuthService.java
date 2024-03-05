package com.dyprj.mitd.domain.auth.service;

import com.dyprj.mitd.domain.auth.service.dto.AuthDto;
import com.dyprj.mitd.domain.user.service.dto.UserRequestDto;

public interface AuthService {

    AuthDto.Token signIn(AuthDto.SignIn authDto);

    void signOut(String encryptedRefreshToken, String accessToken);

    void signUp(UserRequestDto.Join userRequestDto);

    String reissueAccessToken(String encryptedRefreshToken);

}
