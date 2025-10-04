package com.example.portfolio.request.wrap;

import com.example.portfolio.request.FrameworkCreateRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class FrameworkListRequest {
    @Valid
    private List<FrameworkCreateRequest> frameworks;
}
