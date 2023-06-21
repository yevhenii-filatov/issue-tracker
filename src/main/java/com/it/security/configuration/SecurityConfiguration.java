package com.it.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.controller.AuthenticationController;
import com.it.controller.ProjectController;
import com.it.controller.TaskController;
import com.it.controller.UserController;
import com.it.security.authentication.AuthenticationFilter;
import com.it.security.authentication.AuthenticationProvider;
import com.it.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Yevhenii Filatov
 * @since 6/2/23
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final RequestMatcher API_PROTECTED_URLS = new OrRequestMatcher(
       new AntPathRequestMatcher(UserController.USERS_MAPPING + "/**"),
       new AntPathRequestMatcher(ProjectController.PROJECTS_MAPPING + "/**"),
       new AntPathRequestMatcher(TaskController.TASKS_MAPPING + "/**")
    );

    private static final RequestMatcher API_WHITELISTED_URLS = new OrRequestMatcher(
       new AntPathRequestMatcher("/swagger-ui.html"),
       new AntPathRequestMatcher("/swagger"),
       new AntPathRequestMatcher("/swagger-ui/**"),
       new AntPathRequestMatcher("/swagger-resources/**"),
       new AntPathRequestMatcher("/swagger-resources"),
       new AntPathRequestMatcher("/v3/api-docs/**"),
       new AntPathRequestMatcher("/proxy/**"),

       new AntPathRequestMatcher(AuthenticationController.AUTHENTICATION_MAPPING + "/login"),
       new AntPathRequestMatcher(AuthenticationController.AUTHENTICATION_MAPPING + "/refresh/token")
    );

    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
           .getSharedObject(AuthenticationManagerBuilder.class)
           .authenticationProvider(authenticationProvider)
           .build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager,
                                                     AuthenticationFailureHandler defaultAuthenticationFailureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(new OrRequestMatcher(API_PROTECTED_URLS), jwtService);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public AuthenticationFailureHandler defaultAuthenticationFailureHandler(ObjectMapper objectMapper) {
        return (request, response, exception) -> {
            Map<String, Object> vars = Map.of(
               TIMESTAMP, Calendar.getInstance().getTime(),
               MESSAGE, exception.getMessage()
            );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().println(objectMapper.writeValueAsString(vars));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationFilter authenticationFilter) throws Exception {
        return http
           .cors(AbstractHttpConfigurer::disable)
           .csrf(AbstractHttpConfigurer::disable)
           .formLogin(AbstractHttpConfigurer::disable)
           .httpBasic(AbstractHttpConfigurer::disable)
           .logout(AbstractHttpConfigurer::disable)
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .authenticationProvider(authenticationProvider)
           .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class)
           .authorizeHttpRequests(customizer -> customizer
              .requestMatchers(API_WHITELISTED_URLS).permitAll()
              .requestMatchers(API_PROTECTED_URLS).authenticated()
           ).build();
    }
}
