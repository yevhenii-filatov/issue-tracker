package com.it.util;

import com.google.common.hash.Hashing;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

public final class Encryptor {
    private Encryptor() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String sha(@NotBlank String text) {
        return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    }

    public static String md5Hex(@NotBlank String text) {
        String firstIteration = DigestUtils.md5DigestAsHex(text.getBytes());
        return DigestUtils.md5DigestAsHex(firstIteration.getBytes());
    }
}
