package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HobbyCreateRequest {
    @NotBlank(message = "趣味名は必須です")
    @Size(max = 255, message = "趣味名は255文字以内で入力してください")
    private String name;
}
