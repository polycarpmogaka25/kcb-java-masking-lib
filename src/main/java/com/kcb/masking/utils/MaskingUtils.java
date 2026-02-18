package com.kcb.masking.utils; // Suggest putting in .utils or .support

import com.kcb.masking.config.MaskingProperties;

public class MaskingUtils {

    public String mask(String value, MaskingProperties.MaskStyle style, String maskCharacter) {
        if (value == null) return null;
        if (value.isEmpty()) return value;

        String maskChar = resolveMaskCharacter(maskCharacter);

        return switch (style) {
            case FULL -> fullMask(value, maskChar);
            case PARTIAL -> partialMask(value, maskChar);
            case LAST4 -> last4Mask(value, maskChar);
        };
    }

    private String fullMask(String value, String maskChar) {
        return maskChar.repeat(value.length());
    }

    private String partialMask(String value, String maskChar) {
        if (value.contains("@")) return maskEmail(value, maskChar);
        if (value.length() <= 2) return fullMask(value, maskChar);

        String visible = value.substring(0, 2);
        String masked = maskChar.repeat(value.length() - 2);
        return visible + masked;
    }

    private String last4Mask(String value, String maskChar) {
        if (value.length() <= 4) return fullMask(value, maskChar);

        int maskedLength = value.length() - 4;
        String masked = maskChar.repeat(maskedLength);
        String visible = value.substring(value.length() - 4);
        return masked + visible;
    }

    private String maskEmail(String email, String maskChar) {
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) return fullMask(email, maskChar);

        String visiblePrefix = email.substring(0, 2);
        String masked = maskChar.repeat(atIndex - 2);
        String domain = email.substring(atIndex);

        return visiblePrefix + masked + domain;
    }

    private String resolveMaskCharacter(String maskCharacter) {
        if (maskCharacter == null || maskCharacter.isBlank()) return "*";
        return maskCharacter.substring(0, 1);
    }
}

