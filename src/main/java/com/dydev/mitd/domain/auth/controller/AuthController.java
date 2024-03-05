package com.dydev.mitd.domain.auth.controller;

import com.dydev.mitd.common.constants.AuthProperties;
import com.dydev.mitd.domain.auth.service.AuthService;
import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CommonApiUrls.API_PACKAGE_AUTH)
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthDto.Token> signIn(@Valid @RequestBody AuthDto.SignIn authDto) {
        AuthDto.Token tokenDto = authService.signIn(authDto);

        // encrypt refresh token
        tokenDto.encryptRefreshToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenDto.getAccessToken());
        headers.set(AuthProperties.HEADER_PREFIX_RT, tokenDto.getRefreshToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(tokenDto, headers, HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        String encryptedRefreshToken = jwtProvider.resolveRefreshToken(request);
        String accessToken = jwtProvider.resolveAccessToken(request);

        authService.signOut(encryptedRefreshToken, accessToken);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String encryptedRefreshToken = jwtProvider.resolveRefreshToken(request);
        String newAccessToken = authService.reissueAccessToken(encryptedRefreshToken);

        jwtProvider.setAccessTokenHeader(newAccessToken, response);

        return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
    }

    @PostMapping("/test")
    public String test() {
        log.info("test api");
        return "test success";
    }

}
