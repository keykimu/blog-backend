package com.example.portfolio.service.common;

import com.example.portfolio.entity.Work;
import com.example.portfolio.exception.NotFoundException;
import com.example.portfolio.mapper.WorkMapper;
import com.example.portfolio.mapstruct.WorkEntityMapper;
import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.admin.WorkResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkMapper workMapper;
    private final WorkEntityMapper workEntityMapper;
    private final AuthUtils authUtils;

    public List<Work> getAllWorks(Long userId) {
        return workMapper.findAllByUserId(userId);
    }

    public Work getWork(Long id, Long userId) {
        Work work = workMapper.findById(id);
        if (work == null) {
            throw new NotFoundException("対象の Work が存在しません");
        }
        authUtils.checkOwnership(userId,work.getUserId());
        return work;
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkResponse createWork(WorkCreateRequest request, Long userId) {
        Work entity = workEntityMapper.toEntity(request);
        entity.setUserId(userId);
        workMapper.insert(entity);
        Work work = workMapper.findById(entity.getId());
        return workEntityMapper.toResponse(work);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkResponse updateWork(Long id, WorkUpdateRequest request, Long userId) {
        Work existingWork = workMapper.findById(id);
        if (existingWork == null) {
            throw new NotFoundException("対象の Work が存在しません");
        }
        authUtils.checkOwnership(userId, existingWork.getUserId());

        Work entity = workEntityMapper.toEntity(request);
        entity.setId(id);
        entity.setUserId(userId);

        workMapper.update(entity);
        Work work = workMapper.findById(entity.getId());
        return workEntityMapper.toResponse(work);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWork(Long id, Long userId) {
        Work work = workMapper.findById(id);
        if (work == null) {
            throw new NotFoundException("対象の Work が存在しません");
        }
        authUtils.checkOwnership(userId, work.getUserId());
        int result = workMapper.delete(id);
        return result > 0;
    }
}
