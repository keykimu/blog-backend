package com.example.portfolio.controller.admin;

import com.example.portfolio.request.SkillsRequest;
import com.example.portfolio.response.ApiErrorResponse;
import com.example.portfolio.response.SkillsResponse;
import com.example.portfolio.service.SkillsService;
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
@Tag(name = "Skills", description = "言語・フレームワーク・その他 API")
@RequestMapping("/api/admin/skills")
@RequiredArgsConstructor
public class SkillsController {
    private final SkillsService skillsService;

    @Operation(summary = "言語・フレームワーク・その他を取得")
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
    public ResponseEntity<SkillsResponse> getAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(skillsService.get(userId));
    }

    @Operation(summary = "言語・フレームワーク・その他技術をまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "サーバーエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<SkillsResponse> saveAll(@Valid @RequestBody SkillsRequest re, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(skillsService.saveAll(re,userId));
    }
}
