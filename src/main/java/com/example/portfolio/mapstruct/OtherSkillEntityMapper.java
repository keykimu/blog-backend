package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.OtherSkill;
import com.example.portfolio.request.OtherSkillRequest;
import com.example.portfolio.response.PublicOtherSkillResponse;
import com.example.portfolio.response.admin.OtherSkillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OtherSkillEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OtherSkill toEntity(OtherSkillRequest request);
    OtherSkillResponse toResponse(OtherSkill entity);
    PublicOtherSkillResponse toPublicResponse(OtherSkill entity);
}
