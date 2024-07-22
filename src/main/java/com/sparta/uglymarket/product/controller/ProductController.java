package com.sparta.uglymarket.product.controller;

import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
import com.sparta.uglymarket.product.dto.ProductResponse;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // 전체 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<GetAllProductsResponse>> getAllProducts() {
        List<GetAllProductsResponse> products = productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //특정 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductByID(@PathVariable long productId) {
        ProductResponse product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
