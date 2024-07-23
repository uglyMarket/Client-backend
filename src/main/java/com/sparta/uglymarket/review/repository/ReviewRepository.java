package com.sparta.uglymarket.review.repository;

import com.sparta.uglymarket.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long productId);

    Optional<Review> findByOrdersId(Long ordersId);

}
