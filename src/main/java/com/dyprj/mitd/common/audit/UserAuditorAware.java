package com.dyprj.mitd.common.audit;

import com.dyprj.mitd.common.utils.CommonObjectUtils;
import com.dyprj.mitd.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String uid = String.valueOf(authentication.getPrincipal());

        return Optional.of(uid);
    }

}
