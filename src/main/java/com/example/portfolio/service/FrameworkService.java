package com.example.portfolio.service;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.mapper.FrameworkMapper;
import com.example.portfolio.mapstruct.FrameworkEntityMapper;
import com.example.portfolio.request.FrameworkCreateRequest;
import com.example.portfolio.request.wrap.FrameworkListRequest;
import com.example.portfolio.response.FrameworkResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrameworkService {
    private final FrameworkMapper frameworkMapper;
    private final FrameworkEntityMapper frameworkEntityMapper;
    private final AuthUtils authUtils;

    public List<FrameworkResponse> getAll(Long userId) {
        List<Framework> list = frameworkMapper.findAllByUserId(userId);
        return list.stream().map(frameworkEntityMapper::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<FrameworkResponse> saveAll(FrameworkListRequest requests,Long userId) {
        // 現在のユーザーのFramework一覧を取得
        List<Framework> existingFrameworks = frameworkMapper.findAllByUserId(userId);
        if (!existingFrameworks.isEmpty()) {
            authUtils.checkOwnership(userId, existingFrameworks.get(0).getUserId());
        }

        // 一旦全削除
        frameworkMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getFrameworks() != null) {
            for (FrameworkCreateRequest req : requests.getFrameworks()) {
                Framework entity = frameworkEntityMapper.toEntity(req);
                entity.setUserId(userId);
                frameworkMapper.insert(entity);
            }
        }
        return getAll(userId);
    }
}
