package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Career;
import com.example.portfolio.request.CareerRequest;
import com.example.portfolio.response.CareerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CareerEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Career toEntity(CareerRequest request);
    CareerResponse toResponse(Career entity);
}
