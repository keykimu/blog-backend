package com.example.portfolio.mapper;

import com.example.portfolio.entity.Language;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LanguageMapper {
    List<Language> findAll();
    void deleteAll();
    void insert(Language request);
}
