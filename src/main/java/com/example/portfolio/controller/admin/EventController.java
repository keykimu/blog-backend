//package com.example.portfolio.controller;
//
//import com.example.portfolio.request.wrap.EventListRequest;
//import com.example.portfolio.response.ApiErrorResponse;
//import com.example.portfolio.response.EventResponse;
//import com.example.portfolio.service.EventService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@Tag(name = "Event", description = "イベント情報 API")
//@RequestMapping("/api/admin/events")
//@RequiredArgsConstructor
//public class EventController {
//    private final EventService eventService;
//
//    @Operation(summary = "イベントを取得")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "取得成功"),
//            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
//                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
//            ),
//            @ApiResponse(responseCode = "500", description = "サーバーエラー",
//                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
//            )
//    })
//    @GetMapping
//    public ResponseEntity<List<EventResponse>> getAll(HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(eventService.getAll(userId));
//    }
//
//    @Operation(summary = "イベントをまとめて登録")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "登録成功"),
//            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
//                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
//            ),
//            @ApiResponse(responseCode = "500", description = "サーバーエラー",
//                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
//            )
//    })
//    @PostMapping
//    public ResponseEntity<List<EventResponse>> saveAll(@Valid @RequestBody EventListRequest requests,HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(eventService.saveAll(requests,userId));
//    }
//}
