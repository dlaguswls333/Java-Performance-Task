package com.dgsw.javaperformancetask.trade.service;

import com.dgsw.javaperformancetask.common.exception.CustomException;
import com.dgsw.javaperformancetask.common.exception.ErrorCode;
import com.dgsw.javaperformancetask.trade.dto.TradeCreateRequestDto;
import com.dgsw.javaperformancetask.trade.dto.TradeResponseDto;
import com.dgsw.javaperformancetask.trade.dto.TradeStatusUpdateRequestDto;
import com.dgsw.javaperformancetask.trade.entity.TradeEntity;
import com.dgsw.javaperformancetask.trade.entity.TradeStatus;
import com.dgsw.javaperformancetask.trade.repository.TradeRepository;
import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import com.dgsw.javaperformancetask.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final ProductRepository productRepository;

    @Override
    public TradeResponseDto createTrade(TradeCreateRequestDto request) {
        // 상품 정보와 거래 요청 정보를 검증한다.
        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (request.buyerId().equals(request.sellerId())) {
            throw new CustomException(ErrorCode.SELF_TRADE_NOT_ALLOWED);
        }
        if (!product.getPrice().equals(request.price())) {
            throw new CustomException(ErrorCode.TRADE_PRICE_MISMATCH);
        }
        if (!product.getSellerId().equals(request.sellerId())) {
            throw new CustomException(ErrorCode.TRADE_SELLER_MISMATCH);
        }

        // 거래 생성 요청을 엔티티로 변환한다.
        TradeEntity trade = new TradeEntity(
                request.productId(),
                request.buyerId(),
                request.sellerId(),
                request.price()
        );

        // 거래를 저장하고 응답 DTO로 변환한다.
        return new TradeResponseDto(tradeRepository.save(trade));
    }

    @Override
    public TradeResponseDto readTrade(Long id) {
        return new TradeResponseDto(findTrade(id));
    }

    @Override
    public List<TradeResponseDto> readBuyerTrades(Long buyerId) {
        // 구매자의 거래 목록을 응답 DTO 목록으로 변환한다.
        return tradeRepository.findByBuyerId(buyerId)
                .stream()
                .map(TradeResponseDto::new)
                .toList();
    }

    @Override
    public TradeResponseDto updateTradeStatus(
            Long id,
            TradeStatusUpdateRequestDto request
    ) {
        // 거래 상태를 변경하고 저장한다.
        TradeEntity trade = findTrade(id);
        if (trade.getStatus() != TradeStatus.PENDING
                || request.status() == TradeStatus.PENDING) {
            throw new CustomException(ErrorCode.INVALID_TRADE_STATUS);
        }
        trade.changeStatus(request.status());
        return new TradeResponseDto(tradeRepository.save(trade));
    }

    private TradeEntity findTrade(Long id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TRADE_NOT_FOUND));
    }
}
