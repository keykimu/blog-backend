package com.example.portfolio.request.wrap;

import com.example.portfolio.request.HobbyCreateRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class HobbyListRequest {
    @Valid
    private List<HobbyCreateRequest> hobbies;
}
