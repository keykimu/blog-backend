package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.request.FrameworkCreateRequest;
import com.example.portfolio.response.FrameworkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FrameworkEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Framework toEntity(FrameworkCreateRequest request);
    FrameworkResponse toResponse(Framework entity);
    List<FrameworkResponse> toResponseList(List<Framework> entities);
}
