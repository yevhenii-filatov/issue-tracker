package com.it.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static <T> NotFoundException ofId(T id, Class<?> type) {
        return new NotFoundException(String.format("%s not found by ID = %s", type.getSimpleName(), id));
    }
}
