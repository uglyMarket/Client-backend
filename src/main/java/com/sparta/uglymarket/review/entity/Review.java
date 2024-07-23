package com.sparta.uglymarket.review.entity;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.dto.ReviewCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String reviewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public Review(ReviewCreateRequest request, Orders orders, Product product) {
        this.content = request.getContent();
        this.reviewImage = request.getReviewImage();
        this.orders = orders;
        this.product = product;
        orders.markAsReviewed();// 리뷰 작성 시 주문의 reviewed 필드를 true로 설정

    }
}
