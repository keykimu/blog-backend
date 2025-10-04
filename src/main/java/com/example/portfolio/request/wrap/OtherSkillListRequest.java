package com.example.portfolio.request.wrap;

import com.example.portfolio.request.OtherSkillRequest;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class OtherSkillListRequest {
    @Valid
    private List<OtherSkillRequest> otherSkills;
}
