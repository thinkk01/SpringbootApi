package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.Cart;
import com.example.springsecurityjwt.entity.Product;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.repository.CartRepository;
import com.example.springsecurityjwt.repository.ProductRepository;
import com.example.springsecurityjwt.repository.UserRepository;
import com.example.springsecurityjwt.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    @Override
    public Integer addProductToCart(Integer productId, Integer quantity, Integer customerId) {
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(customerId).get();
        Cart cart = cartRepository.findByProductAndUser(productId,customerId);
        if(cart != null){
            Integer updateQuantity = cart.getQuantity() + quantity;
            cart.setQuantity(updateQuantity);
        }else{
            Cart cart1 = new Cart();
            cart1.setQuantity(quantity);
            cart1.setUser(user);
            cart1.setProduct(product);
        }
        Float amount = cart.getQuantity() * product.getPrice();
        cart.setAmount(amount);
        cartRepository.save(cart);
        return productId;
    }

    @Override
    public void updateCart(Integer productId, Integer quantity, Integer customerId) {
        cartRepository.updateQuantity(productId,quantity,customerId);
    }

    @Override
    public void removeCart(Integer productId, Integer customerId) {
        cartRepository.removeCart(productId,customerId);
    }
}
