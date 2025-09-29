package com.example.portfolio.mapper;

import com.example.portfolio.entity.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    Profile find();
    int update(Profile profile);
}
