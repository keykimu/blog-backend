package com.example.portfolio.request;

import com.example.portfolio.request.wrap.FrameworkListRequest;
import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.request.wrap.OtherSkillListRequest;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class SkillsRequest {
    @Valid
    private LanguageListRequest languageListRequest;
    @Valid
    private FrameworkListRequest frameworkListRequest;
    @Valid
    private OtherSkillListRequest otherSkillListRequest;
}
