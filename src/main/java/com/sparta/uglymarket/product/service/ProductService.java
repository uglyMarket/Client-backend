package com.sparta.uglymarket.product.service;

import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    //전체 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //특정 상품 조회
    public Product getProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 없습니다."));
    }

}
