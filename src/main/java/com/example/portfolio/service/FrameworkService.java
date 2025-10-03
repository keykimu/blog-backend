package com.example.portfolio.service;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.mapper.FrameworkMapper;
import com.example.portfolio.mapstruct.FrameworkEntityMapper;
import com.example.portfolio.request.FrameworkCreateRequest;
import com.example.portfolio.response.FrameworkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrameworkService {
    private final FrameworkMapper frameworkMapper;
    private final FrameworkEntityMapper frameworkEntityMapper;

    public List<FrameworkResponse> getAll() {
        List<Framework> list = frameworkMapper.findAll();
        return frameworkEntityMapper.toResponseList(list);
    }

    public List<FrameworkResponse> saveAll(List<FrameworkCreateRequest> requests) {
        frameworkMapper.deleteAll();
        List<Framework> entities = frameworkEntityMapper.toEntityList(requests);
        for (Framework entity : entities) {
            frameworkMapper.insert(entity);
        }
        return frameworkEntityMapper.toResponseList(frameworkMapper.findAll());
    }
}
