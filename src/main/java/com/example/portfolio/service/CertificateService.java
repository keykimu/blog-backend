package com.example.portfolio.service;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.mapper.CertificateMapper;
import com.example.portfolio.mapstruct.CertificateEntityMapper;
import com.example.portfolio.request.CertificateRequest;
import com.example.portfolio.response.CertificateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateEntityMapper certificateEntityMapper;

    public List<CertificateResponse> getAll() {
        return certificateMapper.findAll()
                .stream()
                .map(certificateEntityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<CertificateResponse> saveAll(List<CertificateRequest> requests) {
        certificateMapper.deleteAll();
        for (CertificateRequest req : requests) {
            Certificate entity = certificateEntityMapper.toEntity(req);
            certificateMapper.insert(entity);
        }
        return getAll();
    }
}
