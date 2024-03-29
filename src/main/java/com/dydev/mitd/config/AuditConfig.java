package com.dydev.mitd.config;

import com.dydev.mitd.common.audit.UserAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new UserAuditorAware();
    }

}
