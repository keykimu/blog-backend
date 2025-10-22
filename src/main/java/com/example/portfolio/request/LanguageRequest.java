package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LanguageRequest {
    @NotBlank(message = "言語名は必須です")
    @Size(max = 255, message = "言語名は255文字以内で入力してください")
    private String name;

    @Size(max = 255, message = "言語レベルは255文字以内で入力してください")
    private String level;

    @Size(max = 255, message = "経験歴は255文字以内で入力してください")
    private String experience;
}
