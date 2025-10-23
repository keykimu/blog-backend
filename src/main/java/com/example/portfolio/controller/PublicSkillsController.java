package com.example.portfolio.controller;

import com.example.portfolio.response.ApiErrorResponse;
import com.example.portfolio.response.SkillsResponse;
import com.example.portfolio.service.SkillsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PublicSkills", description = "公開用言語・フレームワーク・その他 API")
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class PublicSkillsController {
    private final SkillsService skillsService;

    @Operation(summary = "公開用言語・フレームワーク・その他を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "サーバーエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<SkillsResponse> getAllByUserId() {
        return ResponseEntity.ok(skillsService.get(1L));
    }
}
