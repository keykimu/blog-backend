package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CertificateRequest {
    @NotBlank(message = "資格取得年は必須です")
    @Size(max = 255, message = "資格取得年は255文字以内で入力してください")
    private String year;

    @NotBlank(message = "資格名は必須です")
    @Size(max = 255, message = "資格名は255文字以内で入力してください")
    private String name;
}
