package com.nan19studio.service;

import com.nan19studio.pojo.Notification;

import java.util.List;

public interface NotificationService {

    // TODO: Get notification list（当前用户全部通知）
    List<Notification> getNotificationList(Integer id);

    // TODO: Mark notification as read（标记通知为已读）
    void markAsRead(Long id, Integer userId);
}
