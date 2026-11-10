package com.dgsw.javaperformancetask.product.service;

import com.dgsw.javaperformancetask.product.dto.ProductCreateRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductResponseDto;
import com.dgsw.javaperformancetask.product.dto.ProductSearchRequestDto;
import com.dgsw.javaperformancetask.product.dto.ProductUpdateRequestDto;
import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import com.dgsw.javaperformancetask.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto request) {
        // 요청 DTO를 상품 엔티티로 변환한다.
        ProductEntity product = new ProductEntity(
            request.title(),
            request.description(),
            request.price(),
            request.sellerId()
        );

        // 상품을 저장하고 응답 DTO로 변환한다.
        ProductEntity savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto readProduct(Long id) {
        // 상품 ID로 상품을 조회한다.
        ProductEntity product = findProduct(id);
        return new ProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> readAllProducts() {
        // 모든 상품을 조회하고 응답 DTO 목록으로 변환한다.
        return productRepository.findAll()
            .stream()
            .map(ProductResponseDto::new)
            .toList();
    }

    @Override
    public List<ProductResponseDto> searchProducts(ProductSearchRequestDto request) {
        // 상품명에 검색어가 포함된 상품을 조회한다.
        return productRepository.findByTitleContainingIgnoreCase(request.keyword())
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto request) {
        // 수정할 상품을 조회하고 변경 내용을 반영한다.
        ProductEntity product = findProduct(id);
        product.update(request.title(), request.description(), request.price());

        // 수정된 상품을 데이터베이스에 저장한다.
        ProductEntity updatedProduct = productRepository.save(product);
        return new ProductResponseDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        // 상품 ID에 해당하는 상품을 삭제한다.
        productRepository.deleteById(id);
    }

    private ProductEntity findProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
