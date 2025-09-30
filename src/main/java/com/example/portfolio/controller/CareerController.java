package com.example.portfolio.controller;

import com.example.portfolio.request.CareerRequest;
import com.example.portfolio.response.CareerResponse;
import com.example.portfolio.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/careers")
public class CareerController {
    private final CareerService careerService;

    @GetMapping
    public ResponseEntity<List<CareerResponse>> getAll() {
        return ResponseEntity.ok(careerService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<CareerResponse>> saveAll(@RequestBody List<CareerRequest> requests) {
        return ResponseEntity.ok(careerService.saveAll(requests));
    }
}
