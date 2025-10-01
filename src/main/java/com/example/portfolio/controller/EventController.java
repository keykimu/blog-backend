package com.example.portfolio.controller;

import com.example.portfolio.request.EventRequest;
import com.example.portfolio.response.EventResponse;
import com.example.portfolio.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<EventResponse>> saveAll(@RequestBody List<EventRequest> requests) {
        return ResponseEntity.ok(eventService.saveAll(requests));
    }
}
