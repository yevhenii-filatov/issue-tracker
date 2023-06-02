package com.it.security.service;

import com.it.model.common.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

/**
 * @author Yevhenii Filatov
 * @since 6/2/23
 */

public interface JwtService {
    Jws<Claims> validate(@NotBlank String token);

    String resolve(@NotNull HttpServletRequest request);

    String generate(@NotNull @Positive Long userId, @NotEmpty Set<Role> userRoles);
}
