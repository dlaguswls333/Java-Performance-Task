package com.dgsw.javaperformancetask.product.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
public class ProductEntity {
    // 이게 PK고 상품의 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 물건의 이름, 100자
    @Column(nullable = false, length = 100)
    private String title;

    // 상품 설명, 1000자
    @Column(nullable = false, length = 1000)
    private String description;

    // 상품 가격, 따로 제한은 없음
    @Column(nullable = false)
    private Integer price;

    // 판매자 회원 ID
    @Column(nullable = false)
    private Long sellerId;

    // LocalDateTime은 yaml 파일로 따로 지정하지 않으면 서버의 시간을 따라감
    // 상품 등록 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 상품 수정 시간
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // JPA 내부 객체 생성용
    protected ProductEntity() {
    }

    // 생성자
    public ProductEntity(
            String title,
            String description,
            Integer price,
            Long sellerId
    ) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 상품 정보를 수정한다.
    public void update(String title, String description, Integer price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }
}
