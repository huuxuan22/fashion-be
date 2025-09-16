package com.example.projectc1023i1.Dto.get_data.order_maptruck;

import com.example.projectc1023i1.model.OrderDetails;
import com.example.projectc1023i1.model.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMaptruck {
    private Long orderId;
    private String orderCode;
    private String status;
    private String paymentType;
    private Double total;
    private LocalDateTime orderDate;
    private String note;
    private Users users;
    private String address;
    private List<OrderDetailMaptruck> orderDetailsList;
}
