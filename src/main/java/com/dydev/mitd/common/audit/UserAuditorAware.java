package com.dydev.mitd.common.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UserAuditorAware implements AuditorAware<String> {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String uid;

        // anonymousUser
        if (authentication.getPrincipal().equals(ANONYMOUS_USER)) {
            uid = String.valueOf(authentication.getPrincipal());
        }
        
        // user
        else {
            uid = ((User) authentication.getPrincipal()).getUsername();
        }

        return Optional.of(uid);
    }

}
