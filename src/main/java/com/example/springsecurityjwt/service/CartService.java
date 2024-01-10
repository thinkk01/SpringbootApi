package com.example.springsecurityjwt.service;

import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Integer addProductToCart(Integer productId, Integer quantity, Integer customerId);
    void updateCart(Integer productId,Integer quantity,Integer customerId);
    void removeCart(Integer productId,Integer customerId);
}
