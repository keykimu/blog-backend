package com.example.portfolio.controller;

import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.response.LanguageResponse;
import com.example.portfolio.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getAll() {
        return ResponseEntity.ok(languageService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<LanguageResponse>> saveAll(@Valid @RequestBody LanguageListRequest requests) {
        return ResponseEntity.ok(languageService.saveAll(requests));
    }
}
