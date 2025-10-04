package com.example.portfolio.request.wrap;

import com.example.portfolio.request.CertificateRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class CertificateListRequest {
    @Valid
    private List<CertificateRequest> certificates;
}
