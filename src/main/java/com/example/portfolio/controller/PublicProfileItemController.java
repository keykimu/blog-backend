package com.example.portfolio.controller;


import com.example.portfolio.response.ApiErrorResponse;
import com.example.portfolio.response.ProfileItemsResponse;
import com.example.portfolio.service.ProfileItemsService;
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
    private final ProfileItemsService profileItemsService;

    @Operation(summary = "公開用趣味・経歴・イベント・資格を取得")
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
    public ResponseEntity<ProfileItemsResponse> getAllByUserId() {
        return ResponseEntity.ok(profileItemsService.get(1L));
    }
}
