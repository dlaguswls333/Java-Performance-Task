package com.dgsw.javaperformancetask.trade.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TradeCreateRequestDto(
        @NotNull Long productId,
        @NotNull Long buyerId,
        @NotNull Long sellerId,
        @NotNull @Positive Integer price
) {
}
