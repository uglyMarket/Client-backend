package com.sparta.uglymarket.product.mapper;

import com.sparta.uglymarket.product.domain.ProductDomain;
import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
import com.sparta.uglymarket.product.dto.ProductResponse;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    // 엔티티 -> 도메인 변환
    public static ProductDomain toDomain(Product product) {
        // 리뷰 ID 리스트를 변환
        List<Long> reviewIds = new ArrayList<>();
        for (Review review : product.getReviews()) {
            reviewIds.add(review.getId());
        }

        //도메인 객체 생성
        return new ProductDomain(
                product.getId(),
                product.getTitle(),
                product.getContent(),
                product.getImage(),
                product.getDeliveryType(),
                product.getPrice(),
                product.isUgly(),
                reviewIds
        );
    }


    // 상품 도메인 -> GetAllProductsResponse (DTO) 변환
    public static GetAllProductsResponse toGetAllProductsResponse(ProductDomain productDomain) {
        return new GetAllProductsResponse(
                productDomain.getId(),
                productDomain.getTitle(),
                productDomain.getContent(),
                productDomain.getImage(),
                productDomain.getDeliveryType(),
                productDomain.getPrice(),
                productDomain.isUgly()
        );
    }

    // 상품 도메인 -> ProductResponse (DTO) 변환
    public static ProductResponse toProductResponse(ProductDomain productDomain) {
        return new ProductResponse(
                productDomain.getId(),
                productDomain.getTitle(),
                productDomain.getContent(),
                productDomain.getImage(),
                productDomain.getDeliveryType(),
                productDomain.getPrice(),
                productDomain.isUgly(),
                productDomain.getReviewIds()
        );
    }

    // Entity 리스트 -> Domain 리스트 변환
    public static List<ProductDomain> toDomainList(List<Product> products) {
        List<ProductDomain> productDomains = new ArrayList<>();
        for (Product product : products) {
            productDomains.add(toDomain(product));
        }
        return productDomains;
    }


    // GetAllProductsResponse 리스트 변환
    public static List<GetAllProductsResponse> toGetAllProductsResponseList(List<ProductDomain> productDomains) {

        List<GetAllProductsResponse> responseList = new ArrayList<>();
        for (ProductDomain productDomain : productDomains) {
            responseList.add(toGetAllProductsResponse(productDomain));
        }
        return responseList;
    }


    // 리뷰 목록에서 리뷰 ID만 추출하는 메서드
    private static List<Long> extractReviewIds(List<Review> reviews) {

        List<Long> reviewIds = new ArrayList<>();
        for (Review review : reviews) {
            reviewIds.add(review.getId()); // 리뷰의 ID만 추가
        }

        return reviewIds;
    }

}

