package com.example.portfolio.request;

import lombok.Data;

@Data
public class WorkUpdateRequest {
    private Long id;

    private String title;
    private String description;
    private String techStack;
    private String url;
}