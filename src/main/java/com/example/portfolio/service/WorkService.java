package com.example.portfolio.service;

import com.example.portfolio.entity.Work;
import com.example.portfolio.mapper.WorkMapper;
import com.example.portfolio.mapstruct.WorkEntityMapper;
import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.WorkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkMapper workMapper;
    private final WorkEntityMapper workEntityMapper;

    public List<WorkResponse> getAllWorks() {
        return workMapper.findAll().stream()
                .map(workEntityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public WorkResponse getWork(Long id) {
        Work work = workMapper.findById(id);
        return workEntityMapper.toResponse(work);
    }

    public WorkResponse createWork(WorkCreateRequest request) {
        Work entity = workEntityMapper.toEntity(request);
        workMapper.insert(entity);
        Work work = workMapper.findById(entity.getId());
        return workEntityMapper.toResponse(work);
    }

    public WorkResponse updateWork(Long id,WorkUpdateRequest request) {
        request.setId(id);
        Work entity = workEntityMapper.toEntity(request);
        workMapper.update(entity);
        Work work = workMapper.findById(entity.getId());
        return workEntityMapper.toResponse(work);
    }

    public boolean deleteWork(Long id) {
        int result = workMapper.delete(id);
        return result > 0;
    }
}
