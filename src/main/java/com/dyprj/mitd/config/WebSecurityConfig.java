package com.dyprj.mitd.config;

import com.dyprj.mitd.common.constants.AuthProperties;
import com.dyprj.mitd.common.constants.CommonApiUrls;
import com.dyprj.mitd.common.filter.JwtAuthenticationFilter;
import com.dyprj.mitd.common.filter.JwtVerificationFilter;
import com.dyprj.mitd.common.provider.JwtProvider;
import com.dyprj.mitd.domain.user.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final UserTokenService userTokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // jwt 사용으로 csrf disabled
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 사용으로 session stateless
                .authorizeHttpRequests(requests ->
                        requests
                                // 모두 허용
                                .requestMatchers(
                                        CommonApiUrls.API_PACKAGE_AUTH + "/sign-in"
                                        , CommonApiUrls.API_PACKAGE_AUTH + "/sign-up")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(jwtProvider, modelMapper, userTokenService))
                .addFilterAfter(new JwtVerificationFilter(jwtProvider), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
        configuration.addExposedHeader(AuthProperties.HEADER_PREFIX_RT);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
