package com.example.portfolio.controller;

import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.response.LanguageResponse;
import com.example.portfolio.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Language", description = "言語情報 API")
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @Operation(summary = "言語を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getAll() {
        return ResponseEntity.ok(languageService.getAll());
    }

    @Operation(summary = "言語をまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @PostMapping
    public ResponseEntity<List<LanguageResponse>> saveAll(@Valid @RequestBody LanguageListRequest requests) {
        return ResponseEntity.ok(languageService.saveAll(requests));
    }
}
