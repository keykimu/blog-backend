package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WorkCreateRequest {
    @NotBlank(message = "タイトルは必須です")
    @Size(max = 100, message = "タイトルは100文字以内で入力してください")
    private String title;

    @NotBlank(message = "説明は必須です")
    @Size(max = 1000, message = "説明は1000文字以内で入力してください")
    private String description;

    @Size(max = 255, message = "タグは255文字以内で入力してください")
    private String techStack;

    // 成果物画像
    private MultipartFile file;
}
