package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CareerRequest {
    @NotBlank(message = "経歴年は必須です")
    @Size(max = 255, message = "経歴年は255文字以内で入力してください")
    private String year;

    @NotBlank(message = "経歴名は必須です")
    @Size(max = 255, message = "経歴名は255文字以内で入力してください")
    private String name;
}
