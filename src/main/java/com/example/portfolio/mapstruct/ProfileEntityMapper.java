package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.PublicProfileResponse;
import com.example.portfolio.response.admin.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProfileEntityMapper {
    ProfileResponse toResponse(Profile entity);

    PublicProfileResponse toPublicResponse(Profile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Profile toEntity(ProfileUpdateRequest request);
}
