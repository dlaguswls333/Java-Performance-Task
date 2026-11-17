package com.dgsw.javaperformancetask.product.controller;

import com.dgsw.javaperformancetask.common.response.ResponseApi;
import com.dgsw.javaperformancetask.product.dto.ProductCreateRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductResponseDto;
import com.dgsw.javaperformancetask.product.dto.ProductSearchRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductUpdateRequestDto;
import com.dgsw.javaperformancetask.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품을 등록한다.
    @PostMapping
    public ResponseEntity<ResponseApi<ProductResponseDto>> createProduct(
            @RequestBody ProductCreateRequestDto request
    ) {
        ProductResponseDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.success("상품 등록 성공", response));
    }

    // 전체 상품을 조회한다.
    @GetMapping
    public ResponseEntity<ResponseApi<List<ProductResponseDto>>> readAllProducts() {
        return ResponseEntity.ok(
                ResponseApi.success("상품 전체 조회 성공", productService.readAllProducts())
        );
    }

    // 상품명을 기준으로 상품을 검색한다.
    @GetMapping("/search")
    public ResponseEntity<ResponseApi<List<ProductResponseDto>>> searchProducts(
            @ModelAttribute ProductSearchRequestDto request
    ) {
        return ResponseEntity.ok(
                ResponseApi.success("상품 검색 성공", productService.searchProducts(request))
        );
    }

    // 상품 하나를 조회한다.
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<ProductResponseDto>> readProduct(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseApi.success("상품 상세 조회 성공", productService.readProduct(id))
        );
    }

    // 상품 정보를 수정한다.
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequestDto request
    ) {
        return ResponseEntity.ok(
                ResponseApi.success("상품 수정 성공", productService.updateProduct(id, request))
        );
    }

    // 상품을 삭제한다.
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResponseApi.success("상품 삭제 성공", null));
    }
}
