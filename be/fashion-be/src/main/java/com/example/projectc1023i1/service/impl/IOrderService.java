package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck;
import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.OrderDetails;
import com.example.projectc1023i1.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    void saveOrder(OrderDetailDTO orderDetailDTO, Users users, List<ProductDetailDTO> productDetailDTOS);
    Page<OrderMaptruck> findAllOrderAdmin(Pageable pageable,String category);
    Integer countOrder(String param);
    Integer countOrderALLUser(@Param("userId") Integer userId);
    void completeOrder(@Param("orderId") Long orderId);
    void deliveryOrder(@Param("orderId") Long orderId);
    Page<OrderMaptruck> findAllOrderUser(Pageable pageable, Integer userId);
    void cancelOrder( Integer userId,  Long orderId);
    boolean existsByOrderIdAndUser( Long orderId, Integer userId);
    Optional<Boolean> isOrderCancelled( Long orderId,  Integer userId);
    Page<OrderMaptruck> findAllOrderAdmin(Pageable pageable);

    Page<OrderMaptruck> findAllOrderCompleteUser(Pageable pageable, String searchTerm, LocalDateTime startDate, LocalDateTime endDate,String paymentMethod);
    Integer getOrdersCompleteAdmin(String searchTerm, LocalDateTime startDate, LocalDateTime endDate,String paymentMethod);
}
