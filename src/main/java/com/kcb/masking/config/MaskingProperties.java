package com.kcb.masking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EnableConfigurationProperties
@AutoConfiguration
@ConfigurationProperties(prefix = "p11.masking")
public class MaskingProperties {

    public MaskStyle maskStyle = MaskStyle.PARTIAL;
    private boolean enabled = true;
    private List<String> fields = new ArrayList<>();
    private String maskCharacter = "*";

    public enum MaskStyle {
        FULL,
        PARTIAL,
        LAST4
    }
}


