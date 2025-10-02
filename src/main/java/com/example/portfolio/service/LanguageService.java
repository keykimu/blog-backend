package com.example.portfolio.service;

import com.example.portfolio.entity.Career;
import com.example.portfolio.entity.Language;
import com.example.portfolio.mapper.LanguageMapper;
import com.example.portfolio.mapstruct.LanguageEntityMapper;
import com.example.portfolio.request.LanguageRequest;
import com.example.portfolio.response.LanguageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageMapper languageMapper;
    private final LanguageEntityMapper languageEntityMapper;

    public List<LanguageResponse> getAll() {
        return languageEntityMapper.toResponseList(languageMapper.findAll());
    }

    public List<LanguageResponse> saveAll(List<LanguageRequest> requests) {
        languageMapper.deleteAll();

        for (LanguageRequest req : requests) {
            Language entity = languageEntityMapper.toEntity(req);
            languageMapper.insert(entity);
        }
        return getAll();
    }
}
