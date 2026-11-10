package com.dgsw.javaperformancetask.product.dto;

public record ProductUpdateRequestDto(
        String title,
        String description,
        Integer price
) {
}
