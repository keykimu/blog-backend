package com.example.portfolio.request;

import lombok.Data;

@Data
public class WorkCreateRequest {
    private String title;
    private String description;
    private String techStack;
    private String url;
}
