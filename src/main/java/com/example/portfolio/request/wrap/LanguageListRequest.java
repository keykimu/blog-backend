package com.example.portfolio.request.wrap;

import com.example.portfolio.request.LanguageRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class LanguageListRequest {
    @Valid
    private List<LanguageRequest> languages;
}
