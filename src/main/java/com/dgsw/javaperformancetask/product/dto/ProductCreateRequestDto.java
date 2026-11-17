package com.dgsw.javaperformancetask.product.dto;

public record ProductCreateRequestDto(
        String title,
        String description,
        Integer price,
        Long sellerId
) {
}
