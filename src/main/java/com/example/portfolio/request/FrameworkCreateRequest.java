package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FrameworkCreateRequest {
    @NotBlank(message = "フレームワーク名は必須です")
    @Size(max = 255, message = "フレームワーク名は255文字以内で入力してください")
    private String name;

    @Size(max = 255, message = "フレームワークレベルは255文字以内で入力してください")
    private String level;
}
