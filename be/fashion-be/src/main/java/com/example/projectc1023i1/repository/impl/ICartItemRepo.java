package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Cart;
import com.example.projectc1023i1.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartItemRepo extends JpaRepository<CartItem, Integer> {
    @Query(value = "select c from Cart c inner join CartItem ci on ci.cart.cartId = c.cartId " +
            "left join Product p on p.productId = ci.product.productId " +
            "left join ProductVariant  pv on pv.productVariantId = ci.productVariant.productVariantId " +
            "where c.user.userId = :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);
}
