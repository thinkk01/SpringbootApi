package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.payload.response.MessageResponse;
import com.example.springsecurityjwt.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<?>addProductToCart(Authentication authentication,
                                             @PathVariable("productId") Integer productId,
                                             @PathVariable("quantity") Integer quantity){
        User user = (User) authentication.getPrincipal();
        Integer result = cartService.addProductToCart(productId,quantity,user.getUserId());
        return new ResponseEntity<MessageResponse>(new MessageResponse(result + "Your product added to your shopping cart"),
                HttpStatus.OK);
    }
    @PutMapping("/update/{productId}/{quantity}")
    public ResponseEntity<?>updateQuantity(Authentication authentication,
                                           @PathVariable("productId") Integer productId,
                                           @PathVariable("quantity") Integer quantity){
        User user = (User) authentication.getPrincipal();
        cartService.updateCart(productId,quantity,user.getUserId());
        return new ResponseEntity<MessageResponse>(new MessageResponse("Update Succesfully!"),HttpStatus.OK);
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCart(Authentication authentication, @PathVariable("productId") Integer productId) {

        User user = (User) authentication.getPrincipal();
        cartService.removeCart(productId, user.getUserId());

        MessageResponse msg = new MessageResponse("Deleted cart sucessfully");

        return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);

    }
}
