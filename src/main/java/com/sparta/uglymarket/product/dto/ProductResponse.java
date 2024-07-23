package com.sparta.uglymarket.product.dto;

import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.dto.ReviewResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductResponse {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String deliveryType;
    private String price;
    private boolean isUgly;
    private List<ReviewResponse> reviews;

    public ProductResponse(Product product, List<ReviewResponse> reviews) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.image = product.getImage();
        this.deliveryType = product.getDeliveryType();
        this.price = product.getPrice();
        this.isUgly = product.isUgly();
        this.reviews = reviews;
    }
}