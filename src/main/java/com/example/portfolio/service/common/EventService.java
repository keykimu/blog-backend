package com.example.portfolio.service.common;

import com.example.portfolio.entity.Event;
import com.example.portfolio.mapper.EventMapper;
import com.example.portfolio.mapstruct.EventEntityMapper;
import com.example.portfolio.request.wrap.EventListRequest;
import com.example.portfolio.request.EventRequest;
import com.example.portfolio.response.admin.EventResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventMapper eventMapper;
    private final EventEntityMapper eventEntityMapper;
    private final AuthUtils authUtils;

    public List<Event> getAll(Long userId) {
        return  eventMapper.findAllByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<EventResponse> saveAll(EventListRequest requests,Long userId) {
        // 現在のユーザーのEvent一覧を取得
        List<Event> existingEvents = eventMapper.findAllByUserId(userId);
        if (!existingEvents.isEmpty()) {
            authUtils.checkOwnership(userId, existingEvents.get(0).getUserId());
        }

        // 一旦全削除
        eventMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getEvents() != null) {
            for (EventRequest req : requests.getEvents()) {
                Event entity = eventEntityMapper.toEntity(req);
                entity.setUserId(userId);
                eventMapper.insert(entity);
            }
        }

        return eventMapper.findAllByUserId(userId)
                .stream()
                .map(eventEntityMapper::toResponse)
                .toList();
    }
}
