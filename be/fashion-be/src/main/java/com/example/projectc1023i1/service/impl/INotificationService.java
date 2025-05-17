package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.get_data.notification_maptruck.NotificationMaptruck;
import com.example.projectc1023i1.model.Notifcation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface INotificationService {
    Page<NotificationMaptruck> findAll(Pageable pageable, Integer userId);
    void save(Notifcation notifcation);
    List<NotificationMaptruck> findAllNotificationNew( Integer userId);
    void updateNotification();
}
