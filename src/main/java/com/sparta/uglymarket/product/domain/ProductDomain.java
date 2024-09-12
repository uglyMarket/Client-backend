package com.sparta.uglymarket.product.domain;


import java.util.List;

public class ProductDomain {

    private Long id;
    private String title;
    private String content;
    private String image;
    private String deliveryType;
    private String price;
    private boolean ugly;
    private List<Long> reviewIds;

    public ProductDomain(Long id, String title, String content, String image, String deliveryType, String price, boolean ugly, List<Long> reviewIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.deliveryType = deliveryType;
        this.price = price;
        this.ugly = ugly;
        this.reviewIds = reviewIds;
    }


    // 비즈니스 로직 (상품을 Ugly 상품으로 설정)
    public void markAsUgly() {
        this.ugly = true;
    }

    // 비즈니스 로직 (가격이 유효한지 확인)
    public boolean isPriceValid() {
        return !this.price.isEmpty() && Integer.parseInt(this.price) > 0;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getPrice() {
        return price;
    }

    public boolean isUgly() {
        return ugly;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }
}
