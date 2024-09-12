package com.sparta.uglymarket.product.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.product.domain.ProductDomain;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.mapper.ProductMapper;
import com.sparta.uglymarket.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    //전체 상품 조회
    public List<ProductDomain> getAllProducts() {

        // 모든 상품을 엔티티로 조회
        List<Product> products = productRepository.findAll();

        // 엔티티 리스트 -> 도메인 리스트로 변환
        return ProductMapper.toDomainList(products);


    }

    // 특정 상품 조회
    public ProductDomain getProductById(long productId) {

        // 특정 상품을 엔티티로 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        // 도메인 객체로 변환
        return ProductMapper.toDomain(product);
    }

}
