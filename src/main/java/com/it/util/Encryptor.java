package com.it.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.token.Sha512DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

public final class Encryptor {
    private Encryptor() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String sha(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        byte[] bytes = Sha512DigestUtils.sha(text.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
