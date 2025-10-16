package com.example.portfolio.mapper;

import com.example.portfolio.entity.Framework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FrameworkMapper {
    List<Framework> findAllByUserId(Long userId);
    void insert(Framework framework);
    void deleteAllByUserId(Long userId);
}
