package com.example.portfolio.service.admin;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.mapstruct.ProfileEntityMapper;
import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.admin.ProfileResponse;
import com.example.portfolio.service.common.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProfileService {
    private final ProfileService profileService;
    private final ProfileEntityMapper profileEntityMapper;
    public ProfileResponse getProfile(Long userId) {
        Profile profile = profileService.getProfile(userId);
        return profileEntityMapper.toResponse(profile);
    }

    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request, Long userId) {
        return profileService.updateProfile(id,request,userId);
    }
}
