package com.example.portfolio.service;

import com.example.portfolio.entity.Language;
import com.example.portfolio.mapper.LanguageMapper;
import com.example.portfolio.mapstruct.LanguageEntityMapper;
import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.request.LanguageRequest;
import com.example.portfolio.response.LanguageResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageMapper languageMapper;
    private final LanguageEntityMapper languageEntityMapper;
    private final AuthUtils authUtils;

    public List<LanguageResponse> getAll(Long userId) {
        List<Language> languages = languageMapper.findAllByUserId(userId);
        return languages.stream().map(languageEntityMapper::toResponseList).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<LanguageResponse> saveAll(LanguageListRequest requests, Long userId) {
        // 現在のユーザーのLanguage一覧を取得
        List<Language> existingLanguages = languageMapper.findAllByUserId(userId);
        if (!existingLanguages.isEmpty()) {
            authUtils.checkOwnership(userId, existingLanguages.get(0).getUserId());
        }

        // 一旦全削除
        languageMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getLanguages() != null) {
            for (LanguageRequest req : requests.getLanguages()) {
                Language entity = languageEntityMapper.toEntity(req);
                entity.setUserId(userId);
                languageMapper.insert(entity);
            }
        }

        return getAll(userId);
    }
}
