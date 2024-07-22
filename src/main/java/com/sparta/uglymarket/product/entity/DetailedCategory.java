package com.sparta.uglymarket.product.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DetailedCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    private String detailedCategoryName;
}
