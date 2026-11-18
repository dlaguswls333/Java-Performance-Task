package com.dgsw.javaperformancetask.trade.dto;

import com.dgsw.javaperformancetask.trade.entity.TradeEntity;
import com.dgsw.javaperformancetask.trade.entity.TradeStatus;

import java.time.LocalDateTime;

public record TradeResponseDto(
        Long id,
        Long productId,
        Long buyerId,
        Long sellerId,
        Integer price,
        TradeStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public TradeResponseDto(TradeEntity trade) {
        this(
                trade.getId(),
                trade.getProductId(),
                trade.getBuyerId(),
                trade.getSellerId(),
                trade.getPrice(),
                trade.getStatus(),
                trade.getCreatedAt(),
                trade.getUpdatedAt()
        );
    }
}
