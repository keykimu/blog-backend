package com.example.portfolio.controller;

import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.WorkResponse;
import com.example.portfolio.service.WorkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

    @GetMapping
    public ResponseEntity<List<WorkResponse>> getAll() {
        List<WorkResponse> works = workService.getAllWorks();
        return ResponseEntity.ok(works);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkResponse> getById(@PathVariable Long id) {
        WorkResponse work = workService.getWork(id);
        if (work != null) {
            return ResponseEntity.ok(work);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest request) {
        WorkResponse createdWork = workService.createWork(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWork);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkResponse> update(@PathVariable Long id, @Valid @RequestBody WorkUpdateRequest request) {
        WorkResponse updatedWork = workService.updateWork(id, request);
        if (updatedWork != null) {
            return ResponseEntity.ok(updatedWork);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = workService.deleteWork(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
