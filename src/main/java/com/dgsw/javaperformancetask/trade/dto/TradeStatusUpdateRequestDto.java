package com.dgsw.javaperformancetask.trade.dto;

import com.dgsw.javaperformancetask.trade.entity.TradeStatus;
import jakarta.validation.constraints.NotNull;

public record TradeStatusUpdateRequestDto(@NotNull TradeStatus status) {
}
