package com.dyprj.mitd.domain.user.service;

import com.dyprj.mitd.domain.user.entity.UserToken;

import java.util.Optional;

public interface UserTokenService {

    Optional<UserToken> getUserTokenByUserId(String userId);

    void updateUserToken(String userId, String refreshToken);

    void deleteUserTokenByUserId(String userId);
}
