package com.dgsw.javaperformancetask.product.service;

import com.dgsw.javaperformancetask.product.dto.ProductCreateRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductResponseDto;
import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import com.dgsw.javaperformancetask.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void 상품을_등록하면_응답_DTO를_반환한다() {
        // 상품 등록 요청을 준비한다.
        ProductCreateRequestDto request = new ProductCreateRequestDto(
                "아이폰 15",
                "상태 좋은 아이폰입니다.",
                800000,
                1L
        );
        ProductEntity product = new ProductEntity(
                request.title(),
                request.description(),
                request.price(),
                request.sellerId()
        );
        given(productRepository.save(any(ProductEntity.class))).willReturn(product);

        ProductResponseDto response = productService.createProduct(request);

        assertThat(response.title()).isEqualTo("아이폰 15");
        assertThat(response.price()).isEqualTo(800000);
    }
}
