package com.example.portfolio.response;

import lombok.Data;

import java.util.List;

@Data
public class PublicSkillsResponse {
    private List<PublicLanguageResponse> languageResponse;
    private List<PublicFrameworkResponse> frameworkResponse;
    private List<PublicOtherSkillResponse> otherSkillResponse;
}
