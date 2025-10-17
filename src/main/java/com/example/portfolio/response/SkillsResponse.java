package com.example.portfolio.response;

import lombok.Data;

import java.util.List;

@Data
public class SkillsResponse {
    private List<LanguageResponse> languageResponse;
    private List<FrameworkResponse> frameworkResponse;
    private List<OtherSkillResponse> otherSkillResponse;
}
