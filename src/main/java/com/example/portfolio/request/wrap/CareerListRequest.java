package com.example.portfolio.request.wrap;

import com.example.portfolio.request.CareerRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class CareerListRequest {
    @Valid
    private List<CareerRequest> careers;
}