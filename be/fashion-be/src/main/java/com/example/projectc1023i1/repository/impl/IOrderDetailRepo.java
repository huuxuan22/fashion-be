package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepo extends JpaRepository<OrderDetails,Long> {

}
