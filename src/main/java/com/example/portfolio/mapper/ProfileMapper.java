package com.example.portfolio.mapper;

import com.example.portfolio.entity.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    Profile findByUserId(Long userId);
    int update(Profile profile);
}
