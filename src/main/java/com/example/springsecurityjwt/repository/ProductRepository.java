package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "SELECT * FROM products WHERE name LIKE %?1%",nativeQuery = true)
    Page<Product>searchProductByName(String keyword, Pageable pageable);
    @Query(value="SELECT * FROM products WHERE price >= ?1 AND price <= ?2",nativeQuery = true)
    Page<Product> searchProductRangePrice(Float startPrice,Float endPrice,Pageable pageable);
    @Query(value="SELECT c FROM Product c WHERE c.id=?1")
    Page<Product> getAllProductByCategory(Integer categoryId,Pageable pageable);
    @Query(value = "SELECT p FROM Product p WHERE p.active=1 ORDER BY p.count_buy DESC LIMIT 12")
    List<Product> get12TopProduct();

}
