package com.example.portfolio.controller;

import com.example.portfolio.request.CertificateRequest;
import com.example.portfolio.response.CertificateResponse;
import com.example.portfolio.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<List<CertificateResponse>> getAll() {
        return ResponseEntity.ok(certificateService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<CertificateResponse>> saveAll(@RequestBody List<CertificateRequest> requests) {
        return ResponseEntity.ok(certificateService.saveAll(requests));
    }
}
