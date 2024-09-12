package com.sparta.uglymarket.product.dto;

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
    private boolean ugly;
    private List<Long> reviewIds;

    public ProductResponse(Long id, String title, String content, String image, String deliveryType, String price, boolean ugly, List<Long> reviewIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.deliveryType = deliveryType;
        this.price = price;
        this.ugly = ugly;
        this.reviewIds = reviewIds;
    }
}