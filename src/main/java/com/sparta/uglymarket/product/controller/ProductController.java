package com.sparta.uglymarket.product.controller;

import com.sparta.uglymarket.product.domain.ProductDomain;
import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
import com.sparta.uglymarket.product.dto.ProductResponse;
import com.sparta.uglymarket.product.mapper.ProductMapper;
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

        // 서비스에서 도메인 리스트를 가져옴
        List<ProductDomain> productDomains = productService.getAllProducts();

        // 도메인 리스트 -> DTO 리스트 변환 (ProductMapper 사용)
        List<GetAllProductsResponse> responseList = ProductMapper.toGetAllProductsResponseList(productDomains);


        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // 특정 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductByID(@PathVariable long productId) {

        // 서비스에서 도메인 객체를 가져오기
        ProductDomain productDomain = productService.getProductById(productId);

        // 도메인 -> DTO 변환 (ProductMapper 사용)
        ProductResponse response = ProductMapper.toProductResponse(productDomain);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
