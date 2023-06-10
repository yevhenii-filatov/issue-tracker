package com.it.service;

import com.it.model.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

public interface UserService {
    User register(@NotNull @Valid User user);

    User findById(@NotNull @Positive Long userId);

    User deleteById(@NotNull @Positive Long userId);

    User update(@NotNull @Positive Long userId, @NotNull @Valid User user);
}
