package com.dgsw.javaperformancetask.trade.service;

import com.dgsw.javaperformancetask.trade.dto.TradeCreateRequestDto;
import com.dgsw.javaperformancetask.trade.dto.TradeResponseDto;
import com.dgsw.javaperformancetask.trade.dto.TradeStatusUpdateRequestDto;

import java.util.List;

public interface TradeService {

    // 거래를 생성한다.
    TradeResponseDto createTrade(TradeCreateRequestDto request);

    // 거래 하나를 조회한다.
    TradeResponseDto readTrade(Long id);

    // 구매자의 거래 내역을 조회한다.
    List<TradeResponseDto> readBuyerTrades(Long buyerId);

    // 거래 상태를 변경한다.
    TradeResponseDto updateTradeStatus(
            Long id,
            TradeStatusUpdateRequestDto request
    );
}
