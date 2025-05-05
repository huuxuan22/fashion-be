package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICartRepo extends JpaRepository<Cart, Long> {
    @Query("delete Cart c where c.cartId = :cartId")
    @Modifying
    @Transactional
    void deleteByCart(@Param("cartId") Long cartId);
}
