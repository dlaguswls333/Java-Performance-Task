package com.dgsw.javaperformancetask.product.service;

import com.dgsw.javaperformancetask.product.dto.ProductCreateRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductResponseDto;
import com.dgsw.javaperformancetask.product.dto.ProductSearchRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductUpdateRequestDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductCreateRequestDto request);
    ProductResponseDto readProduct(Long id);
    List<ProductResponseDto> readAllProducts();
    List<ProductResponseDto> searchProducts(ProductSearchRequestDto request);
    ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto request);
    void deleteProduct(Long id);
}
