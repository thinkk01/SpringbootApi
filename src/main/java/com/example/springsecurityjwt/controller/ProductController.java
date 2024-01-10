package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.entity.Product;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.model.dto.ProductDto;
import com.example.springsecurityjwt.model.request.CreateProductReq;
import com.example.springsecurityjwt.payload.response.MessageResponse;
import com.example.springsecurityjwt.service.ProductService;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Integer id) {
        Optional<Product> product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") Integer id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Delete is success!");
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") int pageNo,
                                                          @RequestParam(defaultValue = "10") int pageSize,
                                                          @RequestParam(defaultValue = "productId") String sortBy) {
        List<Product> getAllProduct = productService.getAllProduct();
        List<ProductDto> getAllProductDto = getAllProduct.stream().map(product ->
                ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .detail(product.getDetail())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .build()
        ).collect(Collectors.toList());
        return new ResponseEntity<List<ProductDto>>(getAllProductDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductReq createProductReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        productService.create(createProductReq, user);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Create Product Success!"), HttpStatus.OK);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ProductDto>> getProductByName(@RequestParam("productName") String productName,
                                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                             @RequestParam(defaultValue = "productId") String sortBy) {
        List<Product> products = productService.getProductByName(productName, pageNo, pageSize, sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());
        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/search/priceRange")
    public ResponseEntity<List<ProductDto>> getProductByRangePrice(@RequestParam(name = "startPrice", defaultValue = "0") Float startPrice,
                                                                   @RequestParam(name = "endPrice") Float endPrice,
                                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                                   @RequestParam(defaultValue = "prductId") String sortBy) {
        List<Product> products = productService.getProductRangePrice(startPrice,endPrice,pageNo,pageSize,sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());
        return new ResponseEntity<List<ProductDto>>(dtos,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>>getProductByCategory(@PathVariable(name="categoryId") Integer categoryId,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                                @RequestParam(defaultValue = "productId") String sortBy){
        List<Product> products = productService.getAllProductByCategory(categoryId,
                pageNo, pageSize, sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }
    @GetMapping("/top12")
    public ResponseEntity<List<ProductDto>> getTop12Product(){
        List<Product>products = productService.getTop12Product();
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());
        return new ResponseEntity<List<ProductDto>>(dtos,HttpStatus.OK);
    }
}
