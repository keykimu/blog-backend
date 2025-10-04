package com.example.portfolio.request.wrap;

import com.example.portfolio.request.EventRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class EventListRequest {
    @Valid
    private List<EventRequest> events;
}