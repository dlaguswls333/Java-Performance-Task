package com.dgsw.javaperformancetask.product.controller;

import com.dgsw.javaperformancetask.product.dto.ProductCreateRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductResponseDto;
import com.dgsw.javaperformancetask.product.service.ProductService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductController controller = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void 상품을_등록하면_201과_JSON_응답을_반환한다() throws Exception {
        // 상품 등록 요청을 JSON으로 전송한다.
        ProductCreateRequestDto request = new ProductCreateRequestDto(
                "아이폰 15",
                "상태 좋은 아이폰입니다.",
                800000,
                1L
        );
        ProductResponseDto response = new ProductResponseDto(
                1L,
                request.title(),
                request.description(),
                request.price(),
                request.sellerId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        given(productService.createProduct(any(ProductCreateRequestDto.class)))
                .willReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("아이폰 15"))
                .andExpect(jsonPath("$.price").value(800000));
    }
}
