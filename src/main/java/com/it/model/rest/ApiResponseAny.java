package com.it.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @author Yevhenii Filatov
 * @since 4/7/23
 */

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseAny <T> extends AbstractApiResponse {
    private final T value;

    public Optional<T> getValue() {
        return Optional.ofNullable(this.value);
    }

    public static <T> ApiResponseAny<T> of(T value) {
        return new ApiResponseAny<>(value);
    }

    public static <T> ApiResponseAny<T> empty() {
        return new ApiResponseAny<>(null);
    }
}
