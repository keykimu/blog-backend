package com.example.portfolio.service;

import com.example.portfolio.entity.Language;
import com.example.portfolio.mapper.LanguageMapper;
import com.example.portfolio.mapstruct.LanguageEntityMapper;
import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.request.LanguageRequest;
import com.example.portfolio.response.LanguageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageMapper languageMapper;
    private final LanguageEntityMapper languageEntityMapper;

    public List<LanguageResponse> getAll() {
        return languageEntityMapper.toResponseList(languageMapper.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public List<LanguageResponse> saveAll(LanguageListRequest requests) {
        languageMapper.deleteAll();

        for (LanguageRequest req : requests.getLanguages()) {
            Language entity = languageEntityMapper.toEntity(req);
            languageMapper.insert(entity);
        }
        return getAll();
    }
}
