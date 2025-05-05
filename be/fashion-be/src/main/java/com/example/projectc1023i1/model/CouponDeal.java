//package com.example.projectc1023i1.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "coupon_deals")
//public class CouponDeal {
//    @Id
//    private int couponId;
//    @Id
//    private int dealId;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;  // Tham chiếu tới Coupon
//
//    @ManyToOne
//    @JoinColumn(name = "deal_id")
//    private Deal deal;
//}
