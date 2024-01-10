package com.example.springsecurityjwt.entity;

import com.example.springsecurityjwt.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="products")
@Builder
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_slug")
    private String slug;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_detail")
    private String detail;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_price")
    private Float price;

    @Column(name = "product_count_buy")
    private int count_buy;
    @Column(name = "product_active")
    private int active;

    @ManyToOne
    @JoinColumn(name = "product_created_by")
    private User user;

    @Column(name = "product_created_at")
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<ProductSize> sizes = new ArrayList<>();

    public Product() {
    }

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .detail(detail)
                .price(price)
                .build();

    }
}
