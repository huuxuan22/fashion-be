package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderDetailMaptruck;
import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.OrderDetails;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    OrderMaptruck toOrderMaptruck(Order order);
    OrderDetailMaptruck toOrderDetailMaptruck(OrderDetails orderDetails);

    default Page<OrderMaptruck> toFeedbackDTOPage(Page<Order> orders) {
        return orders.map(this::toOrderMaptruck);
    }
}
