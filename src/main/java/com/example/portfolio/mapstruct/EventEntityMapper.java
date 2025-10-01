package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Event;
import com.example.portfolio.request.EventRequest;
import com.example.portfolio.response.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventEntityMapper {
    EventEntityMapper INSTANCE = Mappers.getMapper(EventEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Event toEntity(EventRequest request);
    EventResponse toResponse(Event entity);
    List<EventResponse> toResponseList(List<Event> entities);
}
