package com.nan19studio.service.impl;

import com.nan19studio.mapper.NotificationMapper;
import com.nan19studio.pojo.Notification;
import com.nan19studio.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotificationList(Integer id) {
        return notificationMapper.findByUserId(id);
    }
}
