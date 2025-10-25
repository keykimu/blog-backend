package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Event;
import com.example.portfolio.request.EventRequest;
import com.example.portfolio.response.PublicEventResponse;
import com.example.portfolio.response.admin.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Event toEntity(EventRequest request);
    EventResponse toResponse(Event entity);
    PublicEventResponse toPublicResponse(Event entity);
}
