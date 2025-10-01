package com.example.portfolio.mapper;

import com.example.portfolio.entity.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {
    List<Event> findAll();
    void deleteAll();
    void insert(Event event);
}
