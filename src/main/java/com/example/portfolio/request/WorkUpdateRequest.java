package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WorkUpdateRequest {
    private Long id;
    @NotBlank(message = "タイトルは必須です")
    private String title;
    @NotBlank(message = "説明は必須です")
    private String description;
    private String techStack;
    private String url;
}