package com.dydev.mitd.common.provider;

import com.dydev.mitd.common.constants.AuthProperties;
import com.dydev.mitd.common.constants.CommonConstants;
import com.dydev.mitd.common.exception.exception.ApiException;
import com.dydev.mitd.common.exception.message.ErrorMessage;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.common.utils.CommonStringUtils;
import com.dydev.mitd.common.utils.CookieUtils;
import com.dydev.mitd.common.utils.ErrorLogUtils;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import com.dydev.mitd.domain.user.enums.UserTypes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String ROLES_KEY = "roles";

    private final String secret;
    private final long accessTokenValidityInMilliSeconds;
    private final long refreshTokenValidityInMilliSeconds;
    private final String issuer;

    private Key key;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.secret = secret;
        this.accessTokenValidityInMilliSeconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliSeconds = refreshTokenValidityInSeconds * 1000;
        this.issuer = issuer;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * generate token
     */
    public AuthDto.TokenWithRefresh generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        String accessToken = createAccessToken(username, authorities);

        String refreshToken = createRefreshToken(username);

        return AuthDto.TokenWithRefresh.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * create access token
     */
    private String createAccessToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Set<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, UserTypes.USER)
                .claim(ROLES_KEY, roles)
                .setExpiration(getTokenExpiration(accessTokenValidityInMilliSeconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuer(issuer)
                .setIssuedAt(getIssuedAt())
                .compact();
    }

    /**
     * create refresh token
     */
    private String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(getTokenExpiration(refreshTokenValidityInMilliSeconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuer(issuer)
                .setIssuedAt(getIssuedAt())
                .compact();
    }

    private Date getTokenExpiration(long validityMilliseconds) {
        return new Date(new Date().getTime() + validityMilliseconds);
    }

    private Timestamp getIssuedAt() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * get authentication
     */
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (CommonObjectUtils.isNull(claims.get(AUTHORITIES_KEY))) {
            throw new ApiException(ErrorMessage.TOKEN_WITHOUT_CREDENTIAL);
        }

        // get roles
        List<String> roles = claims.get(ROLES_KEY, ArrayList.class);

        // set authorities
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        UserDetails principal = new User(claims.getSubject(), CommonConstants.EMPTY, authorities);
        return new UsernamePasswordAuthenticationToken(principal, CommonConstants.EMPTY, authorities);
    }

    /**
     * validate token
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            ErrorLogUtils.printError(log, e, "Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            ErrorLogUtils.printError(log, e , "Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            ErrorLogUtils.printError(log, e , "Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            ErrorLogUtils.printError(log, e , "JWT claims string is empty");
        }

        return false;
    }

    /**
     * parse token
     */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * set token header
     * 1. access token
     * 2. refresh token
     */
    public void setAccessTokenHeader(String accessToken, HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, AuthProperties.GRANT_TYPE_PREFIX + accessToken);
    }

    public void setRefreshTokenHeader(String refreshToken, HttpServletResponse response) {
        response.setHeader(AuthProperties.HEADER_PREFIX_RT, refreshToken);
    }

    /**
     * resolve token
     * 1. access token
     * 2. refresh token
     */
    public String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (CommonStringUtils.hasText(token) && token.startsWith(AuthProperties.GRANT_TYPE)) {
            return token.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookieValue(AuthProperties.HEADER_PREFIX_RT, request);

        if (CommonObjectUtils.isNull(cookie)) {
            throw new ApiException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND);
        }

        String token = cookie.getValue();

        return CommonStringUtils.hasText(token) ? token : null;
    }

}
