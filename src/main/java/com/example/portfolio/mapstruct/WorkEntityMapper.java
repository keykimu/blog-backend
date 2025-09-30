package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Work;
import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.WorkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkEntityMapper {
    WorkEntityMapper INSTANCE = Mappers.getMapper(WorkEntityMapper.class);

    WorkResponse toResponse(Work work);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Work toEntity(WorkCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Work toEntity(WorkUpdateRequest request);
}
