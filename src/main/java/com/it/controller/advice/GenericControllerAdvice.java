package com.it.controller.advice;

import com.it.model.rest.ApiResponseAny;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

/**
 * @author Yevhenii Filatov
 * @since 6/27/23
 */

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GenericControllerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ApiResponseAny<String> handle(Exception ex, HandlerMethod handlerMethod) {
        log.warn("Handling undefined exception: {}", ex.getMessage(), ex);
        log.warn("Exception source - {}: {}", handlerMethod.getMethod().getDeclaringClass(), handlerMethod.getMethod().getName());
        return ApiResponseAny.of(String.format("Request failed - %s: %s", ex.getClass().getName(), ex.getMessage()));
    }
}
