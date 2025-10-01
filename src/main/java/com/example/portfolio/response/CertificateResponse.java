package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificateResponse {
    private Long id;
    private String year;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
