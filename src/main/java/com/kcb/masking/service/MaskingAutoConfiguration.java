package com.kcb.masking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kcb.masking.config.MaskingProperties;
import com.kcb.masking.utils.MaskingSerializer;
import com.kcb.masking.utils.MaskingUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "p11.masking",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MaskingAutoConfiguration {

    @Bean
    public MaskingUtils maskingEngine() {
        return new MaskingUtils();
    }

    @Bean
    public ObjectMapper objectMapper(MaskingProperties properties, MaskingUtils engine) {
        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addSerializer(Object.class, new MaskingSerializer(properties, engine));
        mapper.registerModule(module);
        return mapper;
    }
}
