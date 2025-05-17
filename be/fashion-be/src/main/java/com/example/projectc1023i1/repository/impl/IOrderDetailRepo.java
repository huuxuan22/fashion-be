package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderDetailRepo extends JpaRepository<OrderDetails,Long> {
    @Query("select o from Order o  " +
            "where o.status = 'CREATE' OR o.status = 'DELIVERY'")
    Page<Order> findAllOrderAdmin(Pageable pageable);
}
