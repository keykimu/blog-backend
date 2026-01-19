package com.example.portfolio.controller.admin;

import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.response.admin.ProfileResponse;
import com.example.portfolio.service.admin.AdminProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Profile", description = "プロフィール情報 API")
@RequestMapping("/api/admin/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final AdminProfileService adminProfileService;

    @Operation(summary = "プロフィールを取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "404", description = "対象が存在しない",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(adminProfileService.getProfile(userId));
    }

    @Operation(summary = "プロフィールを更新")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "他人のデータは操作できません",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "対象が存在しない",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileUpdateRequest updateRequest, HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(adminProfileService.updateProfile(id, updateRequest, userId));
    }
}
