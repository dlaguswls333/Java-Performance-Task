package com.dgsw.javaperformancetask.trade.service;

import com.dgsw.javaperformancetask.common.exception.CustomException;
import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import com.dgsw.javaperformancetask.product.repository.ProductRepository;
import com.dgsw.javaperformancetask.trade.dto.TradeCreateRequestDto;
import com.dgsw.javaperformancetask.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TradeServiceExceptionTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void 상품가격과_거래가격이_다르면_예외가_발생한다() {
        // 상품 가격과 다른 가격으로 거래를 요청한다.
        ProductEntity product = new ProductEntity(
                "아이폰 15",
                "상태 좋은 아이폰입니다.",
                800000,
                1L
        );
        TradeCreateRequestDto request = new TradeCreateRequestDto(
                1L,
                2L,
                1L,
                700000
        );
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        assertThatThrownBy(() -> tradeService.createTrade(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("상품 가격과 거래 가격이 다릅니다.");
    }
}
