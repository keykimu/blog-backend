package com.example.portfolio.service;

import com.example.portfolio.entity.OtherSkill;
import com.example.portfolio.mapper.OtherSkillMapper;
import com.example.portfolio.mapstruct.OtherSkillEntityMapper;
import com.example.portfolio.request.wrap.OtherSkillListRequest;
import com.example.portfolio.request.OtherSkillRequest;
import com.example.portfolio.response.OtherSkillResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OtherSkillService {
    private final OtherSkillMapper otherSkillMapper;
    private final OtherSkillEntityMapper otherSkillEntityMapper;
    private final AuthUtils authUtils;

    public List<OtherSkillResponse> getAll(Long userId) {
        List<OtherSkill> otherSkills = otherSkillMapper.findAllByUserId(userId);
        return otherSkills.stream().map(otherSkillEntityMapper::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OtherSkillResponse> saveAll(OtherSkillListRequest requests, Long userId) {
        // 現在のユーザーのOtherSkill一覧を取得
        List<OtherSkill> existingSkills = otherSkillMapper.findAllByUserId(userId);
        if (!existingSkills.isEmpty()) {
            authUtils.checkOwnership(userId, existingSkills.get(0).getUserId());
        }

        // 一旦全削除
        otherSkillMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getOtherSkills() != null) {
            for (OtherSkillRequest req : requests.getOtherSkills()) {
                OtherSkill entity= otherSkillEntityMapper.toEntity(req);
                entity.setUserId(userId);
                otherSkillMapper.insert(entity);
            }
        }

        return getAll(userId);
    }
}
