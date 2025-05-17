package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.get_data.notification_maptruck.NotificationMaptruck;
import com.example.projectc1023i1.model.Notifcation;
import com.example.projectc1023i1.repository.impl.INotificationRepo;
import com.example.projectc1023i1.service.impl.INotificationService;
import com.example.projectc1023i1.service.mapper.INotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepo notificationRepo;
    @Autowired
    private INotificationMapper notificationMapper;

    @Override
    public Page<NotificationMaptruck> findAll(Pageable pageable, Integer userId) {
        Page<Notifcation> notifcations = notificationRepo.findAllNotification(userId,pageable);
        return notificationMapper.toFeedbackDTOPage(notifcations);
    }

    @Override
    public void save(Notifcation notifcation) {
//        notificationRepo.save
    }

    @Override
    public List<NotificationMaptruck> findAllNotificationNew(Integer userId) {
        List<Notifcation> notifcations = notificationRepo.findAllNotificationNew(userId);
        List<NotificationMaptruck> notificationMaptrucks = new ArrayList<>();
        for (Notifcation notifcation : notifcations) {
            notificationMaptrucks.add(notificationMapper.toNotificationMaptruck(notifcation));
        }
        return notificationMaptrucks;
    }

    @Override
    public void updateNotification() {
        notificationRepo.updateNotification();
    }
}
