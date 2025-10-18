package com.example.portfolio.request;

import com.example.portfolio.request.wrap.CareerListRequest;
import com.example.portfolio.request.wrap.CertificateListRequest;
import com.example.portfolio.request.wrap.EventListRequest;
import com.example.portfolio.request.wrap.HobbyListRequest;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class ProfileItemsRequest {
    @Valid
    private HobbyListRequest hobbyListRequest;
    @Valid
    private CareerListRequest careerListRequest;
    @Valid
    private EventListRequest eventListRequest;
    @Valid
    private CertificateListRequest certificateListRequest;
}
