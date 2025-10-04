package com.example.portfolio.service;

import com.example.portfolio.entity.Career;
import com.example.portfolio.mapstruct.CareerEntityMapper;
import com.example.portfolio.mapper.CareerMapper;
import com.example.portfolio.request.wrap.CareerListRequest;
import com.example.portfolio.request.CareerRequest;
import com.example.portfolio.response.CareerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerMapper careerMapper;
    private final CareerEntityMapper mapper;

    public List<CareerResponse> getAll() {
        return careerMapper.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CareerResponse> saveAll(CareerListRequest requests) {
        // 既存データを削除
        careerMapper.deleteAll();

        // 新規追加
        for (CareerRequest req : requests.getCareers()) {
            Career entity = mapper.toEntity(req);
            careerMapper.insert(entity);
        }

        // 保存後に全件返却
        return getAll();
    }
}
