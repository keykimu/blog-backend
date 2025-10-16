package com.example.portfolio.mapper;

import com.example.portfolio.entity.Hobby;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HobbyMapper {
    List<Hobby> findAllByUserId(Long userId);
    int insert(Hobby profile);
    int deleteAllByUserId(Long userId);
}
