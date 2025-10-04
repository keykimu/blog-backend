package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventRequest {
    @NotBlank(message = "年は必須です")
    private String year;
    @NotBlank(message = "名前は必須です")
    private String name;
}
