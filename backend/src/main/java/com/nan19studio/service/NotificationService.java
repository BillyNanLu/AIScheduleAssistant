package com.nan19studio.service;

import com.nan19studio.pojo.Notification;

import java.util.List;

public interface NotificationService {

    // TODO: Get notification list（当前用户全部通知）
    List<Notification> getNotificationList(Integer id);
}
