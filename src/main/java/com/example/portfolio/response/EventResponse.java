package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Long id;
    private String year;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
