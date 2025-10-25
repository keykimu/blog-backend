package com.example.portfolio.response;

import lombok.Data;

import java.util.List;

@Data
public class PublicProfileItemsResponse {
    private List<PublicHobbyResponse> hobbyResponse;
    private List<PublicCareerResponse> careerResponse;
    private List<PublicEventResponse> eventResponse;
    private List<PublicCertificateResponse> certificateResponse;
}
