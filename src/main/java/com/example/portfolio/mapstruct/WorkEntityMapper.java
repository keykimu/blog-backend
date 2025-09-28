package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Work;
import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.WorkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkEntityMapper {
    WorkEntityMapper INSTANCE = Mappers.getMapper(WorkEntityMapper.class);

    WorkResponse toResponse(Work work);

    Work toEntity(WorkCreateRequest request);
    Work toEntity(WorkUpdateRequest request);
}
