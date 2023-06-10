package com.it.controller;

import com.it.model.domain.User;
import com.it.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final String USER_ID_MAPPING = "/{userId}";

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> register(@NotNull @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
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
