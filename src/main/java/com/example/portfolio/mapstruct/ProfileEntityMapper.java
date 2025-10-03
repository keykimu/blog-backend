package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Profile;
import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ProfileEntityMapper {
    ProfileResponse toResponse(Profile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Profile toEntity(ProfileUpdateRequest request);
}
