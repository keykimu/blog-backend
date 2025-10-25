package com.example.portfolio.service.admin;

import com.example.portfolio.entity.Career;
import com.example.portfolio.entity.Certificate;
import com.example.portfolio.entity.Event;
import com.example.portfolio.entity.Hobby;
import com.example.portfolio.mapstruct.CareerEntityMapper;
import com.example.portfolio.mapstruct.CertificateEntityMapper;
import com.example.portfolio.mapstruct.EventEntityMapper;
import com.example.portfolio.mapstruct.HobbyEntityMapper;
import com.example.portfolio.request.ProfileItemsRequest;
import com.example.portfolio.response.admin.ProfileItemsResponse;
import com.example.portfolio.service.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProfileItemsService {
    private final HobbyService hobbyService;
    private final CareerService careerService;
    private final EventService eventService;
    private final CertificateService certificateService;
    private final HobbyEntityMapper hobbyEntityMapper;
    private final CareerEntityMapper careerEntityMapper;
    private final EventEntityMapper eventEntityMapper;
    private final CertificateEntityMapper certificateEntityMapper;
    public ProfileItemsResponse getAllByUserId(Long userId){
        ProfileItemsResponse response = new ProfileItemsResponse();
        List<Hobby> hobby = hobbyService.getAllHobbies(userId);
        response.setHobbyResponse(hobby.stream().map(hobbyEntityMapper::toResponse).toList());

        List<Career> carrer = careerService.getAll(userId);
        response.setCareerResponse(carrer.stream().map(careerEntityMapper::toResponse).toList());

        List<Event> event = eventService.getAll(userId);
        response.setEventResponse(event.stream().map(eventEntityMapper::toResponse).toList());

        List<Certificate> certificate = certificateService.getAll(userId);
        response.setCertificateResponse(certificate.stream().map(certificateEntityMapper::toResponse).toList());
        return response;
    }

    public ProfileItemsResponse saveAllByUserId(ProfileItemsRequest re, Long userId){
        ProfileItemsResponse response = new ProfileItemsResponse();
        response.setHobbyResponse(hobbyService.createHobby(re.getHobbyListRequest(),userId));
        response.setCareerResponse(careerService.saveAll(re.getCareerListRequest(),userId));
        response.setEventResponse(eventService.saveAll(re.getEventListRequest(),userId));
        response.setCertificateResponse(certificateService.saveAll(re.getCertificateListRequest(),userId));

        return response;
    }
}
