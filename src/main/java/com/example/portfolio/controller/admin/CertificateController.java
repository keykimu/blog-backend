//package com.example.portfolio.controller;
//
//import com.example.portfolio.request.wrap.CertificateListRequest;
//import com.example.portfolio.response.ApiErrorResponse;
//import com.example.portfolio.response.CertificateResponse;
//import com.example.portfolio.service.CertificateService;
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
//@RequestMapping("/api/admin/certificates")
//@Tag(name = "Certificate", description = "資格情報 API")
//@RequiredArgsConstructor
//public class CertificateController {
//    private final CertificateService certificateService;
//
//    @Operation(summary = "資格を取得")
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
//    public ResponseEntity<List<CertificateResponse>> getAll(HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(certificateService.getAll(userId));
//    }
//
//    @Operation(summary = "資格をまとめて登録")
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
//    public ResponseEntity<List<CertificateResponse>> saveAll(@Valid @RequestBody CertificateListRequest requests,HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(certificateService.saveAll(requests,userId));
//    }
//}
