package com.dydev.mitd.domain.auth.controller;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.common.provider.JwtProvider;
import com.dydev.mitd.common.utils.CookieUtils;
import com.dydev.mitd.domain.auth.service.AuthService;
import com.dydev.mitd.domain.auth.service.dto.AuthDto;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<AuthDto.Token> signIn(@Valid @RequestBody AuthDto.SignIn authDto, HttpServletResponse response) {
        // sign in
        AuthDto.TokenWithRefresh tokenDto = authService.signIn(authDto);

        String accessToken = tokenDto.getAccessToken();

        // encrypt refresh token
        // add cookie
        response.addCookie(CookieUtils.createTokenCookie(tokenDto.encryptRefreshToken()));

        // set response
        AuthDto.Token responseDto = AuthDto.Token.builder()
                .accessToken(accessToken)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserRequestDto.Join userRequestDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(authService.signUp(userRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.resolveAccessToken(request);

        authService.signOut(accessToken);

        response.addCookie(CookieUtils.removeTokenCookie());

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
    public ResponseEntity<String> test() {
        log.info("test api");
        return new ResponseEntity<>("test success", HttpStatus.OK);
    }

}
