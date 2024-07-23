package com.sparta.uglymarket.product.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
import com.sparta.uglymarket.product.dto.ProductResponse;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.review.dto.ReviewResponse;
import com.sparta.uglymarket.review.entity.Review;
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

        //DTO를 담아줄 리스트
        List<GetAllProductsResponse> responseList = new ArrayList<>();

        for (Product product : products) { //for 문으로 엔티티를 하나씩 뽑아서
            GetAllProductsResponse response = new GetAllProductsResponse(product); //DTO로 만들어주고

            responseList.add(response);//리스트에 담아주기
        }
        return responseList; //리스트 반환
    }

    //특정 상품 조회
    public ProductResponse getProductById(long productId) {

        //특정 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        //상품에 달려있는 리뷰들 조회하기
        List<ReviewResponse> reviews = new ArrayList<>();
        for (Review review : product.getReviews()) {
            reviews.add(new ReviewResponse(review));
        }

        //DTO로 만들어서 반환
        return new ProductResponse(product, reviews);
    }

}
