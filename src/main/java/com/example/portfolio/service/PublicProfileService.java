package com.example.portfolio.service;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.mapstruct.ProfileEntityMapper;
import com.example.portfolio.response.PublicProfileResponse;
import com.example.portfolio.service.common.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicProfileService {
    private final ProfileService profileService;
    private final ProfileEntityMapper profileEntityMapper;
    public PublicProfileResponse getProfile() {
        Profile entity= profileService.getProfile(1L);
        return profileEntityMapper.toPublicResponse(entity);
    }
}
