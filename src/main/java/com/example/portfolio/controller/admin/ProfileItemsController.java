package com.example.portfolio.controller.admin;

import com.example.portfolio.request.ProfileItemsRequest;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.response.admin.ProfileItemsResponse;
import com.example.portfolio.service.admin.AdminProfileItemsService;
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
@Tag(name = "ProfileItems", description = "趣味・経歴・イベント・資格 API")
@RequestMapping("/api/admin/profile-items")
@RequiredArgsConstructor
public class ProfileItemsController {
    private final AdminProfileItemsService adminProfileItemsService;
    @Operation(summary = "趣味・経歴・イベント・資格を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功")
    })
    @GetMapping
    public ResponseEntity<ProfileItemsResponse> getAllByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(adminProfileItemsService.getAllByUserId(userId));
    }

    @Operation(summary = "趣味・経歴・イベント・資格をまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<ProfileItemsResponse> saveAll(@Valid @RequestBody ProfileItemsRequest re, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(adminProfileItemsService.saveAllByUserId(re,userId));
    }
}

