package com.kcb.masking.service;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kcb.masking.config.MaskingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "p11.masking",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MaskingAutoConfiguration {

    @Bean
    public SimpleModule maskingModule(MaskingProperties props) {
        var module = new SimpleModule();
        module.setSerializerModifier(new MaskingSerializerModifier(props));
        return module;
    }
}
