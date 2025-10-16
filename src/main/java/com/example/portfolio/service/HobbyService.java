package com.example.portfolio.service;

import com.example.portfolio.entity.Event;
import com.example.portfolio.entity.Hobby;
import com.example.portfolio.mapper.HobbyMapper;
import com.example.portfolio.mapstruct.HobbyEntityMapper;
import com.example.portfolio.request.HobbyCreateRequest;
import com.example.portfolio.request.wrap.HobbyListRequest;
import com.example.portfolio.response.HobbyResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HobbyService {
    private final HobbyMapper hobbyMapper;
    private final HobbyEntityMapper hobbyEntityMapper;
    private final AuthUtils authUtils;

    public List<HobbyResponse> getAllHobbies(Long userId) {
        List<Hobby> hobby = hobbyMapper.findAllByUserId(userId);
        return hobby.stream().map(hobbyEntityMapper::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<HobbyResponse> createHobby(HobbyListRequest request,Long userId) {
        // 現在のユーザーのHobby一覧を取得
        List<Hobby> existingHobbies = hobbyMapper.findAllByUserId(userId);
        if (!existingHobbies.isEmpty()) {
            authUtils.checkOwnership(userId, existingHobbies.get(0).getUserId());
        }

        // 一旦全削除
        hobbyMapper.deleteAllByUserId(userId);

        // 挿入
        if (request.getHobbies() != null) {
            for (HobbyCreateRequest req : request.getHobbies()) {
                Hobby entity = hobbyEntityMapper.toEntity(req);
                entity.setUserId(userId);
                hobbyMapper.insert(entity);
            }
        }

        return getAllHobbies(userId);
    }
}
