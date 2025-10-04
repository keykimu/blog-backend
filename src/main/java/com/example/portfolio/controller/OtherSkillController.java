package com.example.portfolio.controller;

import com.example.portfolio.request.wrap.OtherSkillListRequest;
import com.example.portfolio.response.OtherSkillResponse;
import com.example.portfolio.service.OtherSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/other-skills")
@RequiredArgsConstructor
public class OtherSkillController {
    private final OtherSkillService otherSkillService;

    @GetMapping
    public ResponseEntity<List<OtherSkillResponse>> getAll() {
        return ResponseEntity.ok(otherSkillService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<OtherSkillResponse>> saveAll(@Valid @RequestBody OtherSkillListRequest requests) {
        return ResponseEntity.ok(otherSkillService.saveAll(requests));
    }
}
