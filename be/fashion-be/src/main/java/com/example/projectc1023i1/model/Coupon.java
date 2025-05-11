package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Integer couponId;
    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "discount_type")
    private String discountType;
    @Column(name = "discount_value")
    private Double discountValue;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "usage_limit")
    private Integer usageLimit;
    @Column(name = "coupon_status")
    private String couponStatus;
    @Column(name = "image_url")
    private String imageUrl;

    // Quan hệ Many-to-Many: Một Coupon có thể áp dụng cho nhiều Deal (qua bảng Coupon_Deals)
//    private List<CouponDeal> couponDeals;
}
