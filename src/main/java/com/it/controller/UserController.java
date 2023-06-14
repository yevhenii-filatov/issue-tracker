package com.it.controller;

import com.it.model.domain.User;
import com.it.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.USERS_MAPPING)
public class UserController {
    public static final String USERS_MAPPING = "/users";
    private static final String USER_ID_MAPPING = "/{userId}";

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> register(@NotNull @Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @GetMapping(USER_ID_MAPPING)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findById(@NotNull @Positive @PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping(USER_ID_MAPPING)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> update(@NotNull @Valid @RequestBody User user,
                                       @NotNull @Positive @PathVariable Long userId) {
        return ResponseEntity.ok(userService.update(userId, user));
    }

    @DeleteMapping(USER_ID_MAPPING)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteById(@NotNull @Positive @PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteById(userId));
    }
}
