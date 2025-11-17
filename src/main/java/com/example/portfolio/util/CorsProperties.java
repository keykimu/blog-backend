package com.example.portfolio.util;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    @NotBlank
    private String allowedOrigins;
}