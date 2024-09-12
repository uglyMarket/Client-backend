package com.sparta.uglymarket.product.dto;

import lombok.Getter;

@Getter
public class GetAllProductsResponse {

    private Long id;
    private String title;
    private String content;
    private String image;
    private String deliveryType;
    private String price;
    private boolean ugly;

    public GetAllProductsResponse(Long id, String title, String content, String image, String deliveryType, String price, boolean ugly) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.deliveryType = deliveryType;
        this.price = price;
        this.ugly = ugly;
    }
}