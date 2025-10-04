package com.example.portfolio.controller;

import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.ProfileResponse;
import com.example.portfolio.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(id, request));
    }
}
