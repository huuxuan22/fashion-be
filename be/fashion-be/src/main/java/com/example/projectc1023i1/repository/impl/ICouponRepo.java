package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICouponRepo extends JpaRepository<Coupon, Long> {
    @Query("select count(c) from Coupon c")
    Integer countCoupon();
}
