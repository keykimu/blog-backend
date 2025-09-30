package com.example.portfolio.service;

import com.example.portfolio.entity.Hobby;
import com.example.portfolio.mapper.HobbyMapper;
import com.example.portfolio.mapstruct.HobbyEntityMapper;
import com.example.portfolio.request.HobbyCreateRequest;
import com.example.portfolio.response.HobbyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HobbyService {
    private final HobbyMapper hobbyMapper;
    private final HobbyEntityMapper hobbyEntityMapper;

    public List<HobbyResponse> getAllHobbies() {
        return hobbyMapper.findAll()
                .stream()
                .map(hobbyEntityMapper::toResponse)
                .toList();
    }

    public List<HobbyResponse> createHobby(List<HobbyCreateRequest> request) {
        hobbyMapper.deleteAll();
        for(HobbyCreateRequest req: request){
            Hobby entity = hobbyEntityMapper.toEntity(req);
            hobbyMapper.insert(entity);
        }
        return hobbyMapper.findAll()
                .stream()
                .map(hobbyEntityMapper::toResponse)
                .toList();
    }
}
