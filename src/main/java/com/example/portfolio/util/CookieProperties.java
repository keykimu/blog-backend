package com.example.portfolio.util;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "cookie")
public class CookieProperties {
    private boolean secure;

    @NotBlank
    private String sameSite;
}
