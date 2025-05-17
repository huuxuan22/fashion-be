package com.example.projectc1023i1.Dto.get_data.notification_maptruck;


import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck;
import com.example.projectc1023i1.model.Coupon;
import com.example.projectc1023i1.model.Deal;
import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NotificationMaptruck {
    private Long notificationId;
    private OrderMaptruck order;
    private Deal deal;
    private String message;
    private LocalDateTime createAt;
    private Users user;
    private Coupon coupon;

}
