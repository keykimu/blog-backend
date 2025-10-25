package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.request.FrameworkCreateRequest;
import com.example.portfolio.response.PublicFrameworkResponse;
import com.example.portfolio.response.admin.FrameworkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FrameworkEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Framework toEntity(FrameworkCreateRequest request);
    FrameworkResponse toResponse(Framework entity);
    PublicFrameworkResponse toPublicResponse(Framework entity);
}
