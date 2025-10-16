package com.example.portfolio.service;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.mapper.CertificateMapper;
import com.example.portfolio.mapstruct.CertificateEntityMapper;
import com.example.portfolio.request.wrap.CertificateListRequest;
import com.example.portfolio.request.CertificateRequest;
import com.example.portfolio.response.CertificateResponse;
import com.example.portfolio.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateEntityMapper certificateEntityMapper;
    private final AuthUtils authUtils;

    public List<CertificateResponse> getAll(Long userId) {
        List<Certificate> certificate = certificateMapper.findAllByUserId(userId);
        return certificate.stream().map(certificateEntityMapper::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CertificateResponse> saveAll(CertificateListRequest requests,Long userId) {
        // 現在のユーザーのCertificate一覧を取得
        List<Certificate> existingCertificates = certificateMapper.findAllByUserId(userId);
        if (!existingCertificates.isEmpty()) {
            authUtils.checkOwnership(userId, existingCertificates.get(0).getUserId());
        }

        // 一旦全削除
        certificateMapper.deleteAllByUserId(userId);

        // 挿入
        if (requests.getCertificates() != null) {
            for (CertificateRequest req : requests.getCertificates()) {
                Certificate entity = certificateEntityMapper.toEntity(req);
                entity.setUserId(userId);
                certificateMapper.insert(entity);
            }
        }

        return getAll(userId);
    }
}
