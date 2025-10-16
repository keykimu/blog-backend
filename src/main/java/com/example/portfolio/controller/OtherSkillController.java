package com.example.portfolio.controller;

import com.example.portfolio.request.wrap.OtherSkillListRequest;
import com.example.portfolio.response.ApiErrorResponse;
import com.example.portfolio.response.OtherSkillResponse;
import com.example.portfolio.service.OtherSkillService;
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

import java.util.List;

@RestController
@Tag(name = "OtherSkill", description = "その他技術情報 API")
@RequestMapping("/api/admin/other-skills")
@RequiredArgsConstructor
public class OtherSkillController {
    private final OtherSkillService otherSkillService;

    @Operation(summary = "その他技術を取得")
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
    public ResponseEntity<List<OtherSkillResponse>> getAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(otherSkillService.getAll(userId));
    }

    @Operation(summary = "その他技術をまとめて登録")
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
    public ResponseEntity<List<OtherSkillResponse>> saveAll(@Valid @RequestBody OtherSkillListRequest re, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(otherSkillService.saveAll(re,userId));
    }
}
