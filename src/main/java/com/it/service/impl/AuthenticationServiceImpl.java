package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.RoleMapper;
import com.it.mapper.UserMapper;
import com.it.model.domain.User;
import com.it.model.domain.UserCredentials;
import com.it.model.entity.RefreshTokenEntity;
import com.it.model.entity.UserEntity;
import com.it.repository.RefreshTokenRepository;
import com.it.repository.UserRepository;
import com.it.security.service.JwtService;
import com.it.service.AuthenticationService;
import com.it.util.Encryptor;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String USER_NOT_FOUND_WITH_CREDENTIALS = "User not found with provided credentials";
    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token: %s";
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final JwtService jwtService;

    @Override
    public User login(@NotNull UserCredentials credentials) {
        return userRepository
           .findByEmailAndPassword(credentials.getEmail(), Encryptor.sha(credentials.getPassword()))
           .map(this::authenticate)
           .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_WITH_CREDENTIALS));
    }

    @Override
    public void updateRefreshToken(String currentToken) {
        refreshTokenRepository.findByToken(Encryptor.md5Hex(currentToken))
           .ifPresentOrElse(currentRefreshTokenEntity -> {
               refreshTokenRepository.delete(currentRefreshTokenEntity);
               authenticate(currentRefreshTokenEntity.getUser());
           }, () -> {
               throw new AccessDeniedException(format(INVALID_REFRESH_TOKEN, currentToken));
           });
    }

    private User authenticate(UserEntity userEntity) {
        String uuid = UUID.randomUUID().toString();
        RefreshTokenEntity refreshToken = RefreshTokenEntity.create(uuid, userEntity);
        userEntity.getRefreshTokens().add(refreshToken);

        UserEntity saved = userRepository.save(userEntity);
        refreshTokenRepository.save(refreshToken);
        String accessToken = jwtService.generate(userEntity.getId(), roleMapper.fromEntities(userEntity.getRoles()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(uuid, accessToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return userMapper.fromEntity(saved);
    }
}
