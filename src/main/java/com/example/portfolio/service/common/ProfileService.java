package com.example.portfolio.service.common;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.exception.NotFoundException;
import com.example.portfolio.mapper.ProfileMapper;
import com.example.portfolio.mapstruct.ProfileEntityMapper;
import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.admin.ProfileResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileEntityMapper profileEntityMapper;
    private final AuthUtils authUtils;

    public Profile getProfile(Long userId) {
        Profile entity = profileMapper.findByUserId(userId);
        if (entity == null) {
            throw new NotFoundException("対象の Profile が存在しません");
        }
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request, Long userId) {
        Profile existingProfile = profileMapper.findByUserId(userId);
        if (existingProfile == null) {
            throw new NotFoundException("対象の Profile が存在しません");
        }
        authUtils.checkOwnership(userId, existingProfile.getUserId());

        Profile entity = profileEntityMapper.toEntity(request);
        entity.setId(id);
        entity.setUserId(userId);

        profileMapper.update(entity);
        Profile updated = profileMapper.findByUserId(userId);
        return profileEntityMapper.toResponse(updated);
    }
}
