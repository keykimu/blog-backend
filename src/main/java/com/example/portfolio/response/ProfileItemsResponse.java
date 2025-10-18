package com.example.portfolio.response;

import lombok.Data;

import java.util.List;

@Data
public class ProfileItemsResponse {
    private List<HobbyResponse> hobbyResponse;
    private List<CareerResponse> careerResponse;
    private List<EventResponse> eventResponse;
    private List<CertificateResponse> certificateResponse;
}
