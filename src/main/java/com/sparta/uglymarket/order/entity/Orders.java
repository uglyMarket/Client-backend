package com.sparta.uglymarket.order.entity;

import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String orderStatus;
    private int quantity;
    private LocalDateTime orderDate;
    private boolean reviewed; // 후기 작성 여부


    public Orders(Long id, User user, Product product, String orderStatus, int quantity, LocalDateTime orderDate, boolean reviewed) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.reviewed = reviewed;
    }
}
