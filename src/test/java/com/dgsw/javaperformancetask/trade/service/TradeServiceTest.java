package com.dgsw.javaperformancetask.trade.service;

import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import com.dgsw.javaperformancetask.product.repository.ProductRepository;
import com.dgsw.javaperformancetask.trade.dto.TradeCreateRequestDto;
import com.dgsw.javaperformancetask.trade.dto.TradeResponseDto;
import com.dgsw.javaperformancetask.trade.entity.TradeStatus;
import com.dgsw.javaperformancetask.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void 상품정보가_일치하면_거래를_생성한다() {
        // 거래 대상 상품과 거래 요청을 준비한다.
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
                800000
        );
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(tradeRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        TradeResponseDto response = tradeService.createTrade(request);

        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.buyerId()).isEqualTo(2L);
        assertThat(response.status()).isEqualTo(TradeStatus.PENDING);
    }
}
