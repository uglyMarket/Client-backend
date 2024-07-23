package com.sparta.uglymarket.order.entity;

import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter
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



    public Orders() {
    }

    public Orders(User user, Product product, OrderRequest request) {
        this.user = user;
        this.product = product;
        this.orderStatus = request.getOrderStatus();
        this.quantity = request.getQuantity();
        this.orderDate = LocalDateTime.now();
    }
}
