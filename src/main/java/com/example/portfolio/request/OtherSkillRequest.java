package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OtherSkillRequest {
    @NotBlank(message = "その他スキル名は必須です")
    @Size(max = 255, message = "その他スキル名は255文字以内で入力してください")
    private String name;

    @Size(max = 255, message = "その他スキルレベルは255文字以内で入力してください")
    private String level;
}
