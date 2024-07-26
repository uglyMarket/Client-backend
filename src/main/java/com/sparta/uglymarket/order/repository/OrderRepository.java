package com.sparta.uglymarket.order.repository;

import com.sparta.uglymarket.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserIdAndReviewedFalse(Long userId); //리뷰작성이 안된 주문찾기

    List<Orders> findAllByUserIdAndReviewedTrue(Long userId); //리뷰작성이 된 주문찾기


}
