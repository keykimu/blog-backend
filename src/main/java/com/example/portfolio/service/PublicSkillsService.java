package com.example.portfolio.service;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.entity.Language;
import com.example.portfolio.entity.OtherSkill;
import com.example.portfolio.mapstruct.FrameworkEntityMapper;
import com.example.portfolio.mapstruct.LanguageEntityMapper;
import com.example.portfolio.mapstruct.OtherSkillEntityMapper;
import com.example.portfolio.response.PublicSkillsResponse;
import com.example.portfolio.service.common.FrameworkService;
import com.example.portfolio.service.common.LanguageService;
import com.example.portfolio.service.common.OtherSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicSkillsService {
    private final LanguageService languageService;
    private final FrameworkService frameworkService;
    private final OtherSkillService otherSkillService;
    private final LanguageEntityMapper languageEntityMapper;
    private final FrameworkEntityMapper frameworkEntityMapper;
    private final OtherSkillEntityMapper otherSkillEntityMapper;
    public PublicSkillsResponse getAllByUserId() {
        Long userId = 1L;
        PublicSkillsResponse response = new PublicSkillsResponse();
        List<Language> languages = languageService.getAll(userId);
        response.setLanguageResponse(languages.stream().map(languageEntityMapper::toPublicResponse).toList());

        List<Framework> frameworks= frameworkService.getAll(userId);
        response.setFrameworkResponse(frameworks.stream().map(frameworkEntityMapper::toPublicResponse).toList());

        List<OtherSkill> otherSkills = otherSkillService.getAll(userId);
        response.setOtherSkillResponse(otherSkills.stream().map(otherSkillEntityMapper::toPublicResponse).toList());

        return response;
    }
}
