package com.example.portfolio.mapstruct;


import com.example.portfolio.entity.Hobby;
import com.example.portfolio.response.HobbyResponse;
import com.example.portfolio.request.HobbyCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HobbyEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Hobby toEntity(HobbyCreateRequest request);

    HobbyResponse toResponse(Hobby entity);
}