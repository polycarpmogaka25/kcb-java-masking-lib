package com.kcb.masking.utils;


import com.kcb.masking.config.MaskingProperties;


public final class MaskingUtils {

    private MaskingUtils() {
        // Utility class should not be instantiated
    }

    public static String applyMask(String value, MaskingProperties.MaskStyle style, String maskChar) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        var effectiveMaskChar = (maskChar == null || maskChar.isEmpty())
                ? "*"
                : maskChar.substring(0, 1);

        return switch (style) {
            case FULL -> effectiveMaskChar.repeat(value.length());
            case LAST4 -> {
                if (value.length() <= 4) yield value;
                yield effectiveMaskChar.repeat(value.length() - 4) + value.substring(value.length() - 4);
            }
            case PARTIAL -> maskPartial(value, effectiveMaskChar);
        };
    }

    private static String maskPartial(String value, String maskChar) {
        if (value.contains("@")) {
            return maskEmail(value, maskChar);
        }

        if (value.length() <= 2) {
            return value;
        }
        return value.substring(0, 2) + maskChar.repeat(value.length() - 2);
    }

    private static String maskEmail(String email, String maskChar) {
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email;
        }

        var prefix = email.substring(0, atIndex);
        var domain = email.substring(atIndex);

        var maskedPrefix = prefix.substring(0, 2) + maskChar.repeat(prefix.length() - 2);

        return maskedPrefix + domain;
    }
}