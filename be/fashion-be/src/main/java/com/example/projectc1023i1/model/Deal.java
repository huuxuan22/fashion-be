package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "deal")
@Entity
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Integer dealId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "deal_type")
    private String dealType;
    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "deal_price")
    private Double dealPrice;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "deal_status")
    private String dealStatus;
    @Column(name = "image_url")
    private String imageUrl;

    // Quan hệ One-to-Many: Một Deal có thể có nhiều Coupon (qua bảng Coupon_Deals)
//    private List<CouponDeal> couponDeals;
}
