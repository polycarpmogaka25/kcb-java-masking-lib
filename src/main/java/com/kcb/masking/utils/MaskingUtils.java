package com.kcb.masking.utils;


import com.kcb.masking.config.MaskingProperties;

public class MaskingUtils {
    private MaskingUtils() {
        /* This utility class should not be instantiated */
    }

    public static String applyMask(String value, MaskingProperties.MaskStyle style, String maskChar) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        // Ensure we have a valid character to repeat
        String repeatChar = (maskChar == null || maskChar.isEmpty()) ? "*" : maskChar;

        return switch (style) {
            case FULL -> repeatChar.repeat(value.length());
            case LAST4 -> value.length() <= 4 ? value :
                    repeatChar.repeat(value.length() - 4) + value.substring(value.length() - 4);
            case PARTIAL -> {
                if (value.contains("@")) { // Email logic
                    int atIndex = value.indexOf("@");
                    if (atIndex <= 2) yield repeatChar.repeat(3) + value.substring(atIndex);
                    yield value.substring(0, 2) + repeatChar.repeat(3) + value.substring(atIndex);
                }
                // Phone or generic logic
                yield value.substring(0, Math.min(value.length(), 2)) + repeatChar.repeat(5);
            }
        };
    }
}