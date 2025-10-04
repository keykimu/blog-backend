package com.example.portfolio.service;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.mapper.ProfileMapper;
import com.example.portfolio.mapstruct.ProfileEntityMapper;
import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileEntityMapper profileEntityMapper;

    public ProfileResponse getProfile() {
        Profile entity = profileMapper.find();
        return profileEntityMapper.toResponse(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        Profile entity = profileEntityMapper.toEntity(request);
        entity.setId(id);
        profileMapper.update(entity);
        Profile updated = profileMapper.find();
        return profileEntityMapper.toResponse(updated);
    }
}
