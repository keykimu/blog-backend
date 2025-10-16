package com.example.portfolio.service;

import com.example.portfolio.entity.Career;
import com.example.portfolio.entity.Framework;
import com.example.portfolio.mapstruct.CareerEntityMapper;
import com.example.portfolio.mapper.CareerMapper;
import com.example.portfolio.request.wrap.CareerListRequest;
import com.example.portfolio.request.CareerRequest;
import com.example.portfolio.response.CareerResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerMapper careerMapper;
    private final CareerEntityMapper careerEntityMapper;
    private final AuthUtils authUtils;

    public List<CareerResponse> getAll(Long userId) {
        List<Career> careers = careerMapper.findAllByUserId(userId);
        return careers.stream().map(careerEntityMapper::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CareerResponse> saveAll(CareerListRequest requests, Long userId) {
        // 現在のユーザーのCareer一覧を取得
        List<Career> existingCareers = careerMapper.findAllByUserId(userId);
        if (!existingCareers.isEmpty()) {
            authUtils.checkOwnership(userId, existingCareers.get(0).getUserId());
        }

        // 一旦全削除
        careerMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getCareers() != null) {
            for (CareerRequest req : requests.getCareers()) {
                Career entity = careerEntityMapper.toEntity(req);
                entity.setUserId(userId);
                careerMapper.insert(entity);
            }
        }

        return getAll(userId);
    }
}
