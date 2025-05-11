package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.CouponDTO;
import com.example.projectc1023i1.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ICouponService {
    void createCoupon(CouponDTO couponDTO) throws IOException;
    Page<Coupon> getCoupons(Pageable pageable);
    void deleteCoupon(Long couponId);
    Integer countTotalCoupons();
}
