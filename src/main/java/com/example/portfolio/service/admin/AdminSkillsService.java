package com.example.portfolio.service.admin;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.entity.Language;
import com.example.portfolio.entity.OtherSkill;
import com.example.portfolio.mapstruct.FrameworkEntityMapper;
import com.example.portfolio.mapstruct.LanguageEntityMapper;
import com.example.portfolio.mapstruct.OtherSkillEntityMapper;
import com.example.portfolio.request.SkillsRequest;
import com.example.portfolio.response.admin.SkillsResponse;
import com.example.portfolio.service.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSkillsService {
    private final LanguageService languageService;
    private final FrameworkService frameworkService;
    private final OtherSkillService otherSkillService;
    private final LanguageEntityMapper languageEntityMapper;
    private final FrameworkEntityMapper frameworkEntityMapper;
    private final OtherSkillEntityMapper otherSkillEntityMapper;

    public SkillsResponse getAllByUserId(Long userId){
        SkillsResponse response = new SkillsResponse();
        List<Language> languages = languageService.getAll(userId);
        response.setLanguageResponse(languages.stream().map(languageEntityMapper::toResponse).toList());

        List<Framework> frameworks = frameworkService.getAll(userId);
        response.setFrameworkResponse(frameworks.stream().map(frameworkEntityMapper::toResponse).toList());

        List<OtherSkill> otherSkills = otherSkillService.getAll(userId);
        response.setOtherSkillResponse(otherSkills.stream().map(otherSkillEntityMapper::toResponse).toList());

        return response;
    }

    public SkillsResponse saveAll(SkillsRequest request, Long userId){
        SkillsResponse response = new SkillsResponse();
        response.setLanguageResponse(languageService.saveAll(request.getLanguageListRequest(),userId));
        response.setFrameworkResponse(frameworkService.saveAll(request.getFrameworkListRequest(),userId));
        response.setOtherSkillResponse(otherSkillService.saveAll(request.getOtherSkillListRequest(),userId));

        return response;
    }
}
