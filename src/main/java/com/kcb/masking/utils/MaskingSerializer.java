package com.kcb.masking.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kcb.masking.config.MaskingProperties;

import java.io.IOException;

public class MaskingSerializer extends StdSerializer<Object> implements ContextualSerializer {

    private final MaskingProperties props;
    private final String fieldName;

    public MaskingSerializer(MaskingProperties props) {
        this(props, null);
    }

    private MaskingSerializer(MaskingProperties props, String fieldName) {
        super(Object.class);
        this.props = props;
        this.fieldName = fieldName;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        var maskedValue = MaskingUtils.applyMask(value.toString(), props.getMaskStyle(), props.getMaskCharacter());
        gen.writeString(maskedValue);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        var name = (property != null) ? property.getName() : null;
        return new MaskingSerializer(this.props, name);
    }
}