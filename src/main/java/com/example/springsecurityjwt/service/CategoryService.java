package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryService {
    Optional<Category> getById(Integer id);
}
