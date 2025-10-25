package com.example.portfolio.service;

import com.example.portfolio.entity.Work;
import com.example.portfolio.mapstruct.WorkEntityMapper;
import com.example.portfolio.response.PublicWorkResponse;
import com.example.portfolio.service.common.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicWorksService {
    private final WorkService workService;
    private final WorkEntityMapper workEntityMapper;
    public List<PublicWorkResponse> getAllByUserId() {
        List<Work> works = workService.getAllWorks(1L);
        return works.stream().map(workEntityMapper::toPublicResponse).toList();
    }

    public PublicWorkResponse getWorks(Long id) {
        Work work = workService.getWork(id,1L);
        return workEntityMapper.toPublicResponse(work);
    }
}
