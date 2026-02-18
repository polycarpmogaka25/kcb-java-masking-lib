package com.kcb.masking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "p11.masking")
public class MaskingProperties {
    private boolean enabled = true;
    private List<String> fields = new ArrayList<>();
    private MaskStyle maskStyle = MaskStyle.PARTIAL;
    private String maskCharacter = "*";

    public enum MaskStyle {
        FULL, PARTIAL, LAST4
    }
}
