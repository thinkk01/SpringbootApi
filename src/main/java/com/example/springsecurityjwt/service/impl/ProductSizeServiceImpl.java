package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.ProductSize;
import com.example.springsecurityjwt.repository.ProductSizeRepository;
import com.example.springsecurityjwt.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ProductSizeServiceImpl implements ProductSizeService {
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Override
    public Optional<ProductSize> findBySize(Integer size) {
        return Optional.ofNullable(productSizeRepository.findBySize(size));
    }

}
