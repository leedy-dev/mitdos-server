package com.dydev.mitd.domain.user.service;

import com.dydev.mitd.domain.user.entity.UserToken;

import java.util.Optional;

public interface UserTokenService {

    Optional<UserToken> getUserTokenByUserId(String userId);

    void updateUserToken(String userId, String refreshToken);

    void deleteUserTokenByUserId(String userId);
}
