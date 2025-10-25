package com.example.portfolio.controller;

import com.example.portfolio.response.PublicProfileResponse;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.service.PublicProfileService;
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
@Tag(name = "PublicProfile", description = "公開用プロフィール情報 API")
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class PublicProfileController {
    private final PublicProfileService publicProfileService;

    @Operation(summary = "公開用プロフィールを取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "対象が存在しない",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "サーバーエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<PublicProfileResponse> getAllByUserId() {
        return ResponseEntity.ok(publicProfileService.getProfile());
    }
}
