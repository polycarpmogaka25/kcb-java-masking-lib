package com.kcb.masking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kcb.masking.config.MaskingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "p11.masking",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MaskingAutoConfiguration {

    @Bean("maskingObjectMapper")
    public ObjectMapper maskingObjectMapper(MaskingProperties props) {
        var mapper = new ObjectMapper();

        var module = new SimpleModule();
        module.setSerializerModifier(new MaskingSerializerModifier(props));

        mapper.registerModule(module);

        return mapper;
    }
}