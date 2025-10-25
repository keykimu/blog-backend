package com.example.portfolio.service;

import com.example.portfolio.entity.Career;
import com.example.portfolio.entity.Certificate;
import com.example.portfolio.entity.Event;
import com.example.portfolio.entity.Hobby;
import com.example.portfolio.mapstruct.CareerEntityMapper;
import com.example.portfolio.mapstruct.CertificateEntityMapper;
import com.example.portfolio.mapstruct.EventEntityMapper;
import com.example.portfolio.mapstruct.HobbyEntityMapper;
import com.example.portfolio.response.PublicProfileItemsResponse;
import com.example.portfolio.service.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicProfileItemsService {
    private final HobbyService hobbyService;
    private final CareerService careerService;
    private final EventService eventService;
    private final CertificateService certificateService;
    private final HobbyEntityMapper hobbyEntityMapper;
    private final CareerEntityMapper careerEntityMapper;
    private final EventEntityMapper eventEntityMapper;
    private final CertificateEntityMapper certificateEntityMapper;
    public PublicProfileItemsResponse getAllByUserId(){
        Long userId=1L;

        PublicProfileItemsResponse response = new PublicProfileItemsResponse();
        List<Hobby> hobby = hobbyService.getAllHobbies(userId);
        response.setHobbyResponse(hobby.stream().map(hobbyEntityMapper::toPublicResponse).toList());

        List<Career> carrer = careerService.getAll(userId);
        response.setCareerResponse(carrer.stream().map(careerEntityMapper::toPublicResponse).toList());

        List<Event> event = eventService.getAll(userId);
        response.setEventResponse(event.stream().map(eventEntityMapper::toPublicResponse).toList());

        List<Certificate> certificates = certificateService.getAll(userId);
        response.setCertificateResponse(certificates.stream().map(certificateEntityMapper::toPublicResponse).toList());
        return response;
    }
}
