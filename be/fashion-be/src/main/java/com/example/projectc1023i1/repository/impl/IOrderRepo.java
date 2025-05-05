package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrderRepo extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.orderCode = :code")
     Order findByOrderCode(@Param("code") String code);
}
