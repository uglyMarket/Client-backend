package com.sparta.uglymarket.product.repository;

import com.sparta.uglymarket.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
