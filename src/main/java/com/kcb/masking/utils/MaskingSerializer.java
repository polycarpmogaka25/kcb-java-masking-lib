package com.kcb.masking.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kcb.masking.annotation.Log;
import com.kcb.masking.config.MaskingProperties;
import com.kcb.masking.utils.MaskingUtils;

import java.io.IOException;

public class MaskingSerializer extends StdSerializer<Object> implements ContextualSerializer {

    private final MaskingProperties props;
    private final boolean forceMasking;

    public MaskingSerializer(MaskingProperties props) {
        this(props, false);
    }

    private MaskingSerializer(MaskingProperties props, boolean forceMasking) {
        super(Object.class);
        this.props = props;
        this.forceMasking = forceMasking;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        var maskedValue = MaskingUtils.applyMask(
                value.toString(),
                props.getMaskStyle(),
                props.getMaskCharacter()
        );
        gen.writeString(maskedValue);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        boolean hasAnnotation = (property != null) && (property.getAnnotation(Log.class) != null);

        if (hasAnnotation) {
            return new MaskingSerializer(this.props, true);
        }
        return this;
    }
}