package com.dgsw.javaperformancetask.trade.controller;

import com.dgsw.javaperformancetask.common.response.ResponseApi;
import com.dgsw.javaperformancetask.trade.dto.TradeCreateRequestDto;
import com.dgsw.javaperformancetask.trade.dto.TradeResponseDto;
import com.dgsw.javaperformancetask.trade.dto.TradeStatusUpdateRequestDto;
import com.dgsw.javaperformancetask.trade.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    // 거래를 생성한다.
    @PostMapping
    public ResponseEntity<ResponseApi<TradeResponseDto>> createTrade(
            @Valid @RequestBody TradeCreateRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.success(
                        "거래 생성 성공",
                        tradeService.createTrade(request)
                ));
    }

    // 거래 하나를 조회한다.
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<TradeResponseDto>> readTrade(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ResponseApi.success(
                "거래 조회 성공",
                tradeService.readTrade(id)
        ));
    }

    // 구매자의 거래 내역을 조회한다.
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<ResponseApi<List<TradeResponseDto>>> readBuyerTrades(
            @PathVariable Long buyerId
    ) {
        return ResponseEntity.ok(ResponseApi.success(
                "거래 내역 조회 성공",
                tradeService.readBuyerTrades(buyerId)
        ));
    }

    // 거래 상태를 변경한다.
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseApi<TradeResponseDto>> updateTradeStatus(
            @PathVariable Long id,
            @Valid @RequestBody TradeStatusUpdateRequestDto request
    ) {
        return ResponseEntity.ok(ResponseApi.success(
                "거래 상태 변경 성공",
                tradeService.updateTradeStatus(id, request)
        ));
    }
}
