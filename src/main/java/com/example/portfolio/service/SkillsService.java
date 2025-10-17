package com.example.portfolio.service;

import com.example.portfolio.request.SkillsRequest;
import com.example.portfolio.response.SkillsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillsService {
    private final LanguageService languageService;
    private final FrameworkService frameworkService;
    private final OtherSkillService otherSkillService;

    public SkillsResponse get(Long userId){
        SkillsResponse response = new SkillsResponse();
        response.setLanguageResponse(languageService.getAll(userId));
        response.setFrameworkResponse(frameworkService.getAll(userId));
        response.setOtherSkillResponse(otherSkillService.getAll(userId));

        return response;
    }

    public SkillsResponse saveAll(SkillsRequest request,Long userId){
        SkillsResponse response = new SkillsResponse();
        response.setLanguageResponse(languageService.saveAll(request.getLanguageListRequest(),userId));
        response.setFrameworkResponse(frameworkService.saveAll(request.getFrameworkListRequest(),userId));
        response.setOtherSkillResponse(otherSkillService.saveAll(request.getOtherSkillListRequest(),userId));

        return response;
    }
}
