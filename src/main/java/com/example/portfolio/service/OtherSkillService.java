package com.example.portfolio.service;

import com.example.portfolio.mapper.OtherSkillMapper;
import com.example.portfolio.mapperstruct.OtherSkillEntityMapper;
import com.example.portfolio.request.OtherSkillRequest;
import com.example.portfolio.response.OtherSkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OtherSkillService {

    private final OtherSkillMapper otherSkillMapper;
    private final OtherSkillEntityMapper otherSkillEntityMapper;

    public List<OtherSkillResponse> getAll() {
        return otherSkillEntityMapper.toResponseList(otherSkillMapper.findAll());
    }

    public List<OtherSkillResponse> saveAll(List<OtherSkillRequest> requests) {
        // 一旦全削除
        otherSkillMapper.deleteAll();

        // 挿入
        for (OtherSkillRequest req : requests) {
            otherSkillMapper.insert(otherSkillEntityMapper.toEntity(req));
        }

        return getAll();
    }
}
