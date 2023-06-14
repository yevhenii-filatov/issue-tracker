package com.it.security.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Yevhenii Filatov
 * @since 6/2/23
 */

@Order(2)
@Component
public class LoginFilter extends OncePerRequestFilter {
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String REFRESH_TOKEN_ENDPOINT = "/auth/refresh/token";

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response) {
            @Override
            public void setStatus(int sc) {
                super.setStatus(sc);
                handleStatus(sc);
            }

            @Override
            public void sendError(int sc, String msg) throws IOException {
                super.sendError(sc, msg);
                handleStatus(sc);
            }

            @Override
            public void sendError(int sc) throws IOException {
                super.sendError(sc);
                handleStatus(sc);
            }

            private void handleStatus(int code) {
                if (code >= 200 && code < 300) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    response.addHeader(REFRESH_TOKEN_HEADER, authentication.getPrincipal().toString());
                    response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + authentication.getCredentials());
                }
            }
        };
        filterChain.doFilter(request, wrapper);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return !servletPath.equals(LOGIN_ENDPOINT) && !servletPath.equals(REFRESH_TOKEN_ENDPOINT);
    }
}
