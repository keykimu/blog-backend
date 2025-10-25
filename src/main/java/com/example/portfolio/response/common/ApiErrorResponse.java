package com.example.portfolio.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;       // HTTP ステータスコード
    private String code;      // 内部エラーコード
    private String error;     // 表示用メッセージ
}