package com.sparta.uglymarket.order.domain;


import java.time.LocalDateTime;

public class OrdersDomain {

    private Long id;
    private Long userId;
    private Long productId;
    private String orderStatus;
    private int quantity;
    private LocalDateTime orderDate;
    private boolean reviewed;

    //생성자
    public OrdersDomain(Long id, Long userId, Long productId, String orderStatus, int quantity, LocalDateTime orderDate, boolean reviewed) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.reviewed = reviewed;
    }

    // 비즈니스 로직 (후기를 작성한 상태로 변경)
    public void markAsReviewed() {
        this.reviewed = true;
    }


    public void unmarkAsReviewed() {
        this.reviewed = false;
    }

    // 비즈니스 로직 (주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증)
    public boolean isOwnedBy(Long userId) {
        return this.userId.equals(userId);
    }

    // 비즈니스 로직 (주문 상태가 null이 아닌지 확인)
    public boolean isValidOrderStatus() {
        return this.orderStatus != null && !this.orderStatus.isEmpty();
    }

    // 비즈니스 로직 (주문 취소)
    public void cancelOrder() {
        this.orderStatus = "CANCELED";
    }



    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public boolean isReviewed() {
        return reviewed;
    }

}