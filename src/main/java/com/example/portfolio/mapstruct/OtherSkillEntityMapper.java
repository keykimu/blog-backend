package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.OtherSkill;
import com.example.portfolio.request.OtherSkillRequest;
import com.example.portfolio.response.OtherSkillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OtherSkillEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OtherSkill toEntity(OtherSkillRequest request);
    OtherSkillResponse toResponse(OtherSkill entity);
    List<OtherSkillResponse> toResponseList(List<OtherSkill> entities);
}
