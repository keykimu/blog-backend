package com.example.portfolio.mapper;

import com.example.portfolio.entity.Career;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CareerMapper {
    List<Career> findAllByUserId(Long userId);
    int insert(Career career);
    int deleteAllByUserId(Long userId);
}
