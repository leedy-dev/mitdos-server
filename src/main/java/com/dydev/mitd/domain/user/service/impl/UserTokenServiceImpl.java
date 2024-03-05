package com.dydev.mitd.domain.user.service.impl;

import com.dydev.mitd.domain.user.entity.UserToken;
import com.dydev.mitd.domain.user.repository.UserTokenRepository;
import com.dydev.mitd.domain.user.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public Optional<UserToken> getUserTokenByUserId(String userId) {
        return userTokenRepository.findById(userId);
    }

    @Override
    public void updateUserToken(String userId, String refreshToken) {
        userTokenRepository.findById(userId)
                .ifPresentOrElse(
                        userToken -> userToken.updateRefreshToken(refreshToken),
                        () -> userTokenRepository.save(
                                UserToken.builder()
                                        .userId(userId)
                                        .refreshToken(refreshToken)
                                        .build()
                        )
                );
    }

    @Override
    @Transactional
    public void deleteUserTokenByUserId(String userId) {
        userTokenRepository.deleteById(userId);
    }

}
