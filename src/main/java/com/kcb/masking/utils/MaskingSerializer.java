package com.kcb.masking.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kcb.masking.annotation.Mask;
import com.kcb.masking.config.MaskingProperties;
import com.kcb.masking.utils.MaskingUtils;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class MaskingSerializer extends JsonSerializer<Object> {

    private final MaskingProperties properties;
    private final MaskingUtils engine;

    public MaskingSerializer(MaskingProperties properties, MaskingUtils engine) {
        this.properties = properties;
        this.engine = engine;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        // Strings are masked if they meet conditions
        if (value instanceof String str) {
            gen.writeString(str); // top-level string not masked (we need field context)
            return;
        }

        gen.writeStartObject();

        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(value);
            } catch (IllegalAccessException e) {
                continue;
            }

            if (fieldValue instanceof String s) {
                gen.writeStringField(fieldName, maskIfSensitive(s, fieldName, field));
            } else if (fieldValue instanceof List<?> list) {
                gen.writeArrayFieldStart(fieldName);
                for (Object item : list) {
                    serialize(item, gen, serializers); // recursive
                }
                gen.writeEndArray();
            } else if (fieldValue != null) {
                gen.writeFieldName(fieldName);
                serialize(fieldValue, gen, serializers); // recursive
            } else {
                gen.writeNullField(fieldName);
            }
        }

        gen.writeEndObject();
    }

    private String maskIfSensitive(String value, String fieldName, Field field) {

        if (!properties.isEnabled()) return value;

        // Mask if field name is in YAML-configured list
        List<String> sensitiveFields = properties.getFields();
        if (sensitiveFields.contains(fieldName)) {
            return engine.mask(value, properties.getMaskStyle(), properties.getMaskCharacter());
        }

        // Mask if field has @Mask annotation
        if (field.isAnnotationPresent(Mask.class)) {
            return engine.mask(value, properties.getMaskStyle(), properties.getMaskCharacter());
        }

        return value;
    }
}
