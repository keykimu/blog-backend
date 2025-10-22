package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EventRequest {
    @NotBlank(message = "イベント参加年は必須です")
    @Size(max = 255, message = "イベント参加年は255文字以内で入力してください")
    private String year;

    @NotBlank(message = "イベント名は必須です")
    @Size(max = 255, message = "イベント名は255文字以内で入力してください")
    private String name;
}
