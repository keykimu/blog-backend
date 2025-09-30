package com.example.portfolio.controller;


import com.example.portfolio.request.HobbyCreateRequest;
import com.example.portfolio.response.HobbyResponse;
import com.example.portfolio.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hobby")
@RequiredArgsConstructor
public class HobbyController {
    private final HobbyService hobbyService;

    @GetMapping
    public ResponseEntity<List<HobbyResponse>> getAll() {
        List<HobbyResponse> hobbies = hobbyService.getAllHobbies();
        return ResponseEntity.ok(hobbies);
    }

    @PostMapping
    public ResponseEntity<List<HobbyResponse>> create(@RequestBody List<HobbyCreateRequest> request) {
        List<HobbyResponse> created = hobbyService.createHobby(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
