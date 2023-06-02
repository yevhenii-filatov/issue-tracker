package com.it.security.service.impl;

import com.it.configuration.properties.JwtProperties;
import com.it.model.common.Role;
import com.it.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author Yevhenii Filatov
 * @since 6/2/23
 */

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String USER_ID_CLAIM = "userId";
    private static final String USER_ROLES_CLAIM = "roles";
    private static final String BEARER_PREFIX = "Bearer: ";

    private final JwtProperties jwtProperties;

    @Override
    public Jws<Claims> validate(String token) {
        try {
            return Jwts.parserBuilder()
               .setSigningKey(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8))
               .build()
               .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }

    @Override
    public String resolve(@NotNull HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header == null || !header.startsWith(BEARER_PREFIX) ? null : header.substring(BEARER_PREFIX.length());
    }

    @Override
    public String generate(@NotNull Long userId, @NotEmpty Set<Role> userRoles) {
        Map<String, Object> claims = Map.of(
           USER_ID_CLAIM, userId,
           USER_ROLES_CLAIM, userRoles
        );

        return Jwts.builder()
           .setClaims(claims)
           .setIssuedAt(new Date(System.currentTimeMillis()))
           .setExpiration(new Date(System.currentTimeMillis() + (48 * 60 * 60 * 1000)))
           .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
           .compact();
    }
}
