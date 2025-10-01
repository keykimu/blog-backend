package com.example.portfolio.service;

import com.example.portfolio.entity.Event;
import com.example.portfolio.mapper.EventMapper;
import com.example.portfolio.mapstruct.EventEntityMapper;
import com.example.portfolio.request.EventRequest;
import com.example.portfolio.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventMapper eventMapper;
    private final EventEntityMapper eventEntityMapper;

    public List<EventResponse> getAll() {
        return eventEntityMapper.toResponseList(eventMapper.findAll());
    }

    public List<EventResponse> saveAll(List<EventRequest> requests) {
        // 全削除
        eventMapper.deleteAll();

        // 再登録
        for (EventRequest request : requests) {
            Event event = eventEntityMapper.toEntity(request);
            eventMapper.insert(event);
        }

        return getAll();
    }
}
