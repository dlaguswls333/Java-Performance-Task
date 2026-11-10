package com.dgsw.javaperformancetask.product.dto;

import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String title,
        String description,
        Integer price,
        Long sellerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public ProductResponseDto(ProductEntity product) {
        this(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getSellerId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
