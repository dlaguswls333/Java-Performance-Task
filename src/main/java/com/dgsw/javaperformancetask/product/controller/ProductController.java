package com.dgsw.javaperformancetask.product.controller;

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
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductCreateRequestDto request
    ) {
        ProductResponseDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 전체 상품을 조회한다.
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> readAllProducts() {
        return ResponseEntity.ok(productService.readAllProducts());
    }

    // 상품명을 기준으로 상품을 검색한다.
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @ModelAttribute ProductSearchRequestDto request
    ) {
        return ResponseEntity.ok(productService.searchProducts(request));
    }

    // 상품 하나를 조회한다.
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> readProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readProduct(id));
    }

    // 상품 정보를 수정한다.
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequestDto request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // 상품을 삭제한다.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
