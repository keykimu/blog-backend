package com.example.portfolio.controller;

import com.example.portfolio.response.PublicProfileItemsResponse;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.service.PublicProfileItemsService;
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
@Tag(name = "PublicProfileItems", description = "公開用趣味・経歴・イベント・資格 API")
@RequestMapping("/api/profile-items")
@RequiredArgsConstructor
public class PublicProfileItemController {
    private final PublicProfileItemsService publicProfileItemsService;

    @Operation(summary = "公開用趣味・経歴・イベント・資格を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功")
    })
    @GetMapping
    public ResponseEntity<PublicProfileItemsResponse> getAllByUserId() {
        return ResponseEntity.ok(publicProfileItemsService.getAllByUserId());
    }
}
