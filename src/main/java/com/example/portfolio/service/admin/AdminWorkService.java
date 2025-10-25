package com.example.portfolio.service.admin;

import com.example.portfolio.entity.Work;
import com.example.portfolio.mapstruct.WorkEntityMapper;
import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.admin.WorkResponse;
import com.example.portfolio.service.common.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminWorkService {
    private final WorkService workService;
    private final WorkEntityMapper workEntityMapper;
    public List<WorkResponse> getAllWorks(Long userId) {
        List<Work> works = workService.getAllWorks(userId);
        return works.stream().map(workEntityMapper::toResponse).toList();
    }

    public WorkResponse getWork(Long id, Long userId) {
        Work work = workService.getWork(id,userId);
        return workEntityMapper.toResponse(work);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkResponse createWork(WorkCreateRequest request, Long userId) {
        return workService.createWork(request,userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkResponse updateWork(Long id, WorkUpdateRequest request, Long userId) {
        return workService.updateWork(id,request,userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWork(Long id, Long userId) {
        return workService.deleteWork(id,userId);
    }
}
