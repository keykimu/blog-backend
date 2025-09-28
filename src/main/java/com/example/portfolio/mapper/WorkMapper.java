package com.example.portfolio.mapper;

import com.example.portfolio.entity.Work;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkMapper {
    List<Work> findAll();
    Work findById(Long id);
    void insert(Work work);
    void update(Work work);
    int delete(Long id);
}
