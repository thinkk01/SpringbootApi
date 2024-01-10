package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.Category;
import com.example.springsecurityjwt.entity.Product;
import com.example.springsecurityjwt.entity.ProductSize;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.model.request.CreateProductReq;
import com.example.springsecurityjwt.repository.ProductRepository;
import com.example.springsecurityjwt.repository.ProductSizeRepository;
import com.example.springsecurityjwt.service.CategoryService;
import com.example.springsecurityjwt.service.ProductService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductSizeRepository productSizeRepository;

    @Override
    public Optional<Product> getById(Integer id) {
        return Optional.ofNullable(productRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void create(CreateProductReq req, User user) {
        Optional<Category> category = categoryService.getById(req.getCategoryId());
        Slugify slugify = new Slugify();
        String slug = slugify.slugify(req.getName());
        List<ProductSize> sizes = new ArrayList<>();
        ProductSize ps = productSizeRepository.findBySize(req.getSize());
        sizes.add(ps);
        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .detail(req.getDetail())
                .price(req.getPrice())
                .slug(slug)
                .created_at(new Date())
                .count_buy(0)
                .user(user)
                .sizes(sizes)
                .category(category.get())
                .build();
        productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductByName(String keyword, Integer pageNo , Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Product>result = productRepository.searchProductByName(keyword,pageable);
        if(result.hasContent()){
            return result.getContent();
        }else{
            return new ArrayList<Product>();
        }
    }

    @Override
    public List<Product> getProductRangePrice(Float startPrice, Float endPrice, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Product> result = productRepository.searchProductRangePrice(startPrice,endPrice,pageable);
        if(result.hasContent()){
            return result.getContent();
        }else{
            return new ArrayList<Product>();
        }

    }

    @Override
    public List<Product> getAllProductByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Product> result = productRepository.getAllProductByCategory(categoryId,pageable);
        if(result.hasContent()){
            return result.getContent();
        }else{
            return new ArrayList<Product>();
        }
    }

    @Override
    public List<Product> getTop12Product() {
        return productRepository.get12TopProduct();
    }

}
