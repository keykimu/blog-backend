package com.example.portfolio.service;

import com.example.portfolio.entity.Framework;
import com.example.portfolio.mapper.FrameworkMapper;
import com.example.portfolio.mapstruct.FrameworkEntityMapper;
import com.example.portfolio.request.wrap.FrameworkListRequest;
import com.example.portfolio.response.FrameworkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public List<FrameworkResponse> saveAll(FrameworkListRequest requests) {
        frameworkMapper.deleteAll();
        List<Framework> entities = frameworkEntityMapper.toEntityList(requests.getFrameworks());
        for (Framework entity : entities) {
            frameworkMapper.insert(entity);
        }
        return frameworkEntityMapper.toResponseList(frameworkMapper.findAll());
    }
}
