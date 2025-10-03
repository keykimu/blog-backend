package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Language;
import com.example.portfolio.request.LanguageRequest;
import com.example.portfolio.response.LanguageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LanguageEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Language toEntity(LanguageRequest request);

    List<LanguageResponse> toResponseList(List<Language> entities);
}
