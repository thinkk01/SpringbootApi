package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.ProductSize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductSizeService {
    Optional<ProductSize> findBySize(Integer size);
}
