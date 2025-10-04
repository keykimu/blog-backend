package com.example.portfolio.controller;

import com.example.portfolio.request.wrap.EventListRequest;
import com.example.portfolio.response.EventResponse;
import com.example.portfolio.service.EventService;
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
@Tag(name = "Event", description = "イベント情報 API")
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(summary = "イベントを取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Operation(summary = "イベントをまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @PostMapping
    public ResponseEntity<List<EventResponse>> saveAll(@Valid @RequestBody EventListRequest requests) {
        return ResponseEntity.ok(eventService.saveAll(requests));
    }
}
