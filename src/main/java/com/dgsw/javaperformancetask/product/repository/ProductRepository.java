package com.dgsw.javaperformancetask.product.repository;


import com.dgsw.javaperformancetask.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // 상품명에 검색어가 포함된 상품을 조회한다.
    List<ProductEntity> findByTitleContainingIgnoreCase(String keyword);
}
