package com.example.portfolio.controller;

import com.example.portfolio.request.FrameworkCreateRequest;
import com.example.portfolio.response.FrameworkResponse;
import com.example.portfolio.service.FrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frameworks")
@RequiredArgsConstructor
public class FrameworkController {
    private final FrameworkService frameworkService;

    @GetMapping
    public ResponseEntity<List<FrameworkResponse>> getAll() {
        return ResponseEntity.ok(frameworkService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<FrameworkResponse>> saveAll(@RequestBody List<FrameworkCreateRequest> requests) {
        return ResponseEntity.ok(frameworkService.saveAll(requests));
    }
}
