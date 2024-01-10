package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize,Integer> {
    ProductSize findBySize(Integer size);
}
