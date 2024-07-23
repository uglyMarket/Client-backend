package com.sparta.uglymarket.order.repository;

import com.sparta.uglymarket.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
