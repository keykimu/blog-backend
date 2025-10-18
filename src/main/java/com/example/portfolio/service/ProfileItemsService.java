package com.example.portfolio.service;

import com.example.portfolio.request.ProfileItemsRequest;
import com.example.portfolio.response.ProfileItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileItemsService {
    private final HobbyService hobbyService;
    private final CareerService careerService;
    private final EventService eventService;
    private final CertificateService certificateService;

    public ProfileItemsResponse get(Long userId){
        ProfileItemsResponse response = new ProfileItemsResponse();
        response.setHobbyResponse(hobbyService.getAllHobbies(userId));
        response.setCareerResponse(careerService.getAll(userId));
        response.setEventResponse(eventService.getAll(userId));
        response.setCertificateResponse(certificateService.getAll(userId));

        return response;
    }

    public ProfileItemsResponse saveAll(ProfileItemsRequest request, Long userId){
        ProfileItemsResponse response = new ProfileItemsResponse();
        response.setHobbyResponse(hobbyService.createHobby(request.getHobbyListRequest(),userId));
        response.setCareerResponse(careerService.saveAll(request.getCareerListRequest(),userId));
        response.setEventResponse(eventService.saveAll(request.getEventListRequest(),userId));
        response.setCertificateResponse(certificateService.saveAll(request.getCertificateListRequest(),userId));

        return response;
    }
}
