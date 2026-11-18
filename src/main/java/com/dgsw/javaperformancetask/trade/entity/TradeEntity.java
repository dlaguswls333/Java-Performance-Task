package com.dgsw.javaperformancetask.trade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Getter
public class TradeEntity {

    // 거래 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 거래 대상 상품 ID
    @Column(nullable = false)
    private Long productId;

    // 구매자 회원 ID
    @Column(nullable = false)
    private Long buyerId;

    // 판매자 회원 ID
    @Column(nullable = false)
    private Long sellerId;

    // 거래 당시 상품 가격
    @Column(nullable = false)
    private Integer price;

    // 거래 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TradeStatus status;

    // 거래 생성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 거래 수정 시간
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // JPA 내부 객체 생성용
    protected TradeEntity() {
    }

    public TradeEntity(
            Long productId,
            Long buyerId,
            Long sellerId,
            Integer price
    ) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = TradeStatus.PENDING;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // 거래 상태를 변경한다.
    public void changeStatus(TradeStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
