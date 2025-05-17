package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.notification_maptruck.NotificationMaptruck;
import com.example.projectc1023i1.Dto.get_data.notification_maptruck.OrderMaptruck;
import com.example.projectc1023i1.model.Order;
import org.mapstruct.Mapper;
import com.example.projectc1023i1.model.Notifcation;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface INotificationMapper {
    NotificationMaptruck toNotificationMaptruck(Notifcation notification);
    OrderMaptruck toOrderMaptruck(Order order);
    default Page<NotificationMaptruck> toFeedbackDTOPage(Page<Notifcation> notifcations) {
        return notifcations.map(this::toNotificationMaptruck);
    }
}
