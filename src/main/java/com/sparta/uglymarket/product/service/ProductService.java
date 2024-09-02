package com.sparta.uglymarket.product.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
import com.sparta.uglymarket.product.dto.ProductResponse;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.review.dto.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    //전체 상품 조회
    public List<GetAllProductsResponse> getAllProducts() {

        //전체 상품 조회
        List<Product> products = productRepository.findAll();
        return convertToGetAllProductsResponseList(products);
    }

    //특정 상품 세부조회
    public ProductResponse getProductById(long productId) {

        //특정 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        return convertToProductResponse(product);
    }

    //DTO 변환 메서드
    private List<GetAllProductsResponse> convertToGetAllProductsResponseList(List<Product> products) {
        List<GetAllProductsResponse> responseList = new ArrayList<>();
        for (Product product : products) {
            responseList.add(new GetAllProductsResponse(product));
        }
        return responseList;
    }

    ///DTO 변환 메서드, 상품에 달려있는 리뷰들 조회
    private ProductResponse convertToProductResponse(Product product) {
        List<ReviewResponse> reviews = new ArrayList<>();
        for (var review : product.getReviews()) {
            reviews.add(new ReviewResponse(review));
        }
        return new ProductResponse(product, reviews);
    }

}
