package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
