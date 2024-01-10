package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.Category;
import com.example.springsecurityjwt.repository.CategoryRepository;
import com.example.springsecurityjwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public Optional<Category> getById(Integer id) {
        return categoryRepository.findById(id);
    }
}
