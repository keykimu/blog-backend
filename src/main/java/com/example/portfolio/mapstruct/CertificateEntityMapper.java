package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.request.CertificateRequest;
import com.example.portfolio.response.CertificateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CertificateEntityMapper {
    CertificateEntityMapper INSTANCE = Mappers.getMapper(CertificateEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Certificate toEntity(CertificateRequest request);

    CertificateResponse toResponse(Certificate entity);
}
