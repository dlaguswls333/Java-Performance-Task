package com.dgsw.javaperformancetask.trade.repository;

import com.dgsw.javaperformancetask.trade.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<TradeEntity, Long> {

    // 구매자의 거래 내역을 조회한다.
    List<TradeEntity> findByBuyerId(Long buyerId);
}
