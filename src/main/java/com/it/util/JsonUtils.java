package com.it.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Slf4j
public final class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> T fromString(@NotBlank String json, Class<T> targetType) {
        try {
            return MAPPER.readValue(json, targetType);
        } catch (JsonProcessingException e) {
            String msg = "Can not convert String [{}] to [{}]";
            log.error(msg, json, targetType, e);
            throw new IllegalArgumentException(msg, e);
        }
    }

    public static <T> String asString(@NotNull T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String msg = "Can not convert [{}] to JSON string";
            log.error(msg, object, e);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
