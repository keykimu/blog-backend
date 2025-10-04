package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HobbyCreateRequest {
    @NotBlank(message = "名前は必須です")
    private String name;
}
