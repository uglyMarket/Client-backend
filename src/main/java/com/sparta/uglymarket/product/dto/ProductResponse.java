package com.sparta.uglymarket.product.dto;

import com.sparta.uglymarket.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String deliveryType;
    private String price;
    private boolean isUgly;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.image = product.getImage();
        this.deliveryType = product.getDeliveryType();
        this.price = product.getPrice();
        this.isUgly = product.isUgly();
    }
}