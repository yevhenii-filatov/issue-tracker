package com.it.security.authentication;

import com.it.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yevhenii Filatov
 * @since 6/2/23
 */

@Component
@RequiredArgsConstructor
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final String AUTHENTICATION_HEADER_IS_NOT_PRESENT = "Authentication header isn't present in request";
    private static final String EXPIRED_OR_INVALID_JWT = "Expired or invalid JWT token";
    private static final String UNDEFINED_ROLE = "Undefined role in JWT token";
    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    private final JwtService jwtService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        // unnecessary method
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        String token = (String) authentication.getCredentials();

        if (token == null) {
            throw new AccessDeniedException(AUTHENTICATION_HEADER_IS_NOT_PRESENT);
        }

        Jws<Claims> claimsJws = jwtService.validate(token);
        if (claimsJws == null) {
            throw new AccessDeniedException(EXPIRED_OR_INVALID_JWT);
        }

        List<String> roles = claimsJws.getBody().get(ROLES_CLAIM, List.class);

        if (roles == null || roles.isEmpty()) {
            throw new AccessDeniedException(UNDEFINED_ROLE);
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
           roles.stream().map(role -> ROLE_PREFIX + role.toUpperCase()).toArray(String[]::new)
        );

        return new User(token, token, true, true, true, true, authorities);
    }
}
