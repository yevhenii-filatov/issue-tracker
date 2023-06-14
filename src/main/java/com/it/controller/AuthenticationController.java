package com.it.controller;

import com.it.model.domain.User;
import com.it.model.domain.UserCredentials;
import com.it.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.AUTHENTICATION_MAPPING)
public class AuthenticationController {
    public static final String AUTHENTICATION_MAPPING = "/auth";
    private static final String LOGIN_MAPPING = "/login";
    private static final String UPDATE_REFRESH_TOKEN_MAPPING = "/refresh/token";

    private final AuthenticationService authenticationService;

    @PostMapping(LOGIN_MAPPING)
    public ResponseEntity<User> login(@NotNull @Valid @RequestBody UserCredentials credentials) {
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

    @PostMapping(UPDATE_REFRESH_TOKEN_MAPPING)
    public ResponseEntity<Void> updateRefreshToken(@NotBlank @RequestParam String token) {
        authenticationService.updateRefreshToken(token);
        return ResponseEntity.ok().build();
    }
}
