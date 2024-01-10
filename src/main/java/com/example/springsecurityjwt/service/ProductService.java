package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.Product;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.model.request.CreateProductReq;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> getById(Integer id);

    void deleteById(Integer id);

    List<Product> getAllProduct();
    void create(CreateProductReq req, User user);
    void delete(Integer id);
    List<Product> getProductByName(String keyword,Integer pageNo, Integer pageSize, String sortBy);
    List<Product> getProductRangePrice(Float startPrice,Float endPrice,Integer pageNo,Integer pageSize,String sortBy);
    List<Product> getAllProductByCategory(Integer categoryId,Integer pageNo, Integer pageSize,String sortBy);
    List<Product> getTop12Product();
}
