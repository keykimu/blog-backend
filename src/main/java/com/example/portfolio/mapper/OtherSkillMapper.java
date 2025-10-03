package com.example.portfolio.mapper;

import com.example.portfolio.entity.OtherSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OtherSkillMapper {
    List<OtherSkill> findAll();
    void deleteAll();
    void insert(OtherSkill otherSkill);
}
