package com.sparta.uglymarket.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String farmName;
    private String introMessage;
    private String profileImage;
    private String phoneNumber;
    private String leaderName;
    private String businessId;
    private LocalDate openingDate;
    private String minOrderAmount;
}
