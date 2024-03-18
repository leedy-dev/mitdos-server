package com.dydev.mitd.config;

import com.dydev.mitd.common.constants.AuthProperties;
import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.common.filter.CommonAccessDeniedHandler;
import com.dydev.mitd.common.filter.CommonAuthenticationEntryPoint;
import com.dydev.mitd.common.filter.JwtAuthenticationFilter;
import com.dydev.mitd.common.filter.JwtVerificationFilter;
import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.common.utils.ErrorLogUtils;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.user.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
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
        // setting
        http
                .cors(configurer -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable) // jwt 사용으로 csrf disabled
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // jwt 사용으로 session stateless

        // request - ALL
        http
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(
                                        // sign-in, sign-up
                                        CommonApiUrls.API_PACKAGE_AUTH + "/sign-in"
                                        , CommonApiUrls.API_PACKAGE_AUTH + "/sign-up"
                                        , CommonApiUrls.API_PACKAGE_AUTH + "/reissue"
                                        // exception
                                        , CommonApiUrls.API_PACKAGE_EXCEPTION + "/entry-point"
                                        , CommonApiUrls.API_PACKAGE_EXCEPTION + "/access-denied")
                                .permitAll()
                );

        // request - ADMIN, SUPER
        http
                .authorizeHttpRequests(requests ->
                        requests
                                // USER
                                .requestMatchers(CommonApiUrls.API_PACKAGE_USER, CommonApiUrls.API_PACKAGE_USER + "/*")
                                .hasAnyAuthority(RoleTypes.ROLE_ADMIN.name(), RoleTypes.ROLE_SUPER.name())
                );

        // request - SUPER
        http
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(HttpMethod.POST, CommonApiUrls.API_PACKAGE_MENU)
                                .hasAnyAuthority(RoleTypes.ROLE_SUPER.name())
                                .requestMatchers(HttpMethod.PUT, CommonApiUrls.API_PACKAGE_MENU + "/*")
                                .hasAnyAuthority(RoleTypes.ROLE_SUPER.name())
                                .requestMatchers(HttpMethod.DELETE, CommonApiUrls.API_PACKAGE_MENU + "/*")
                                .hasAnyAuthority(RoleTypes.ROLE_SUPER.name())
                );

        // request - menu
        Arrays.stream(RoleTypes.values())
                .forEach(role ->
                        {
                            try {
                                http.authorizeHttpRequests(requests ->
                                        requests
                                                .requestMatchers(CommonApiUrls.API_PACKAGE_MENU + "/list/" + role.name() + "*")
                                                .hasAnyAuthority(role.name()));
                            } catch (Exception e) {
                                ErrorLogUtils.printError(log, e);
                            }
                        }
                );

        // any request authenticated
        http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());

        // entry point & handler
        http.exceptionHandling(configurer ->
                configurer
                        .authenticationEntryPoint(new CommonAuthenticationEntryPoint())
                        .accessDeniedHandler(new CommonAccessDeniedHandler())
        );

        // filter
        http
                .addFilter(new JwtAuthenticationFilter(jwtProvider, modelMapper, userTokenService))
                .addFilterAfter(new JwtVerificationFilter(jwtProvider), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOrigins(List.of("*")); setAllowCredentials(true) 설정하면 사용 불가
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
        configuration.addExposedHeader(AuthProperties.HEADER_PREFIX_RT);
        configuration.addExposedHeader(AuthProperties.SET_COOKIE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
