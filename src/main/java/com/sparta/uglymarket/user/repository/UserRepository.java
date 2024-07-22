package com.sparta.uglymarket.user.repository;

import com.sparta.uglymarket.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
