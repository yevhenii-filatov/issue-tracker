package com.it.service;

import com.it.model.domain.User;
import com.it.model.domain.UserCredentials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

public interface AuthenticationService {
    User login(@NotNull @Valid UserCredentials credentials);

    void updateRefreshToken(@NotBlank String currentToken);
}
