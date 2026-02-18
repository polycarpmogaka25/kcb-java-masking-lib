package com.kcb.masking.service;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.kcb.masking.config.MaskingProperties;
import com.kcb.masking.utils.MaskingSerializer;

import java.util.List;

public class MaskingSerializerModifier extends BeanSerializerModifier {

    private final MaskingProperties props;

    public MaskingSerializerModifier(MaskingProperties props) {
        this.props = props;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            if (props.getFields().contains(writer.getName())) {
                writer.assignSerializer(new MaskingSerializer(props));
            }
        }
        return beanProperties;
    }
}