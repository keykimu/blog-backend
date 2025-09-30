package com.example.portfolio.mapper;

import com.example.portfolio.entity.Career;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CareerMapper {
    List<Career> findAll();
    int insert(Career career);
    int deleteAll();
}
