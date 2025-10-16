package com.example.portfolio.mapper;

import com.example.portfolio.entity.OtherSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OtherSkillMapper {
    List<OtherSkill> findAllByUserId(Long userId);
    void deleteAllByUserId(Long UserId);
    void insert(OtherSkill otherSkill);
}
