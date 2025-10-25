package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.request.CertificateRequest;
import com.example.portfolio.response.PublicCertificateResponse;
import com.example.portfolio.response.admin.CertificateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CertificateEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Certificate toEntity(CertificateRequest request);

    CertificateResponse toResponse(Certificate entity);
    PublicCertificateResponse toPublicResponse(Certificate entity);
}
