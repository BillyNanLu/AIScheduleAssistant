package com.nan19studio.controller;

import com.nan19studio.pojo.Notification;
import com.nan19studio.pojo.Result;
import com.nan19studio.service.NotificationService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // TODO: Get notification list（当前用户全部通知）
    @GetMapping("/list")
    public Result<List<Notification>> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Notification> notifications = notificationService.getNotificationList(id);
        return Result.success(notifications);
    }

    // TODO: Mark notification as read（标记通知为已读）
    @PutMapping("/read/{id}")
    public Result markAsRead(@PathVariable Long id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        notificationService.markAsRead(id, userId);
        return Result.success("已读");
    }

    // TODO: Add notification（添加通知）
    @PostMapping("/add")
    public Result add(@RequestBody Notification notification) {
        Notification exist = notificationService.getNotificationById(notification.getScheduleId());
        if (exist != null) {
            return Result.error("通知已存在");
        } else {
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("id");
            notification.setUserId(userId.longValue());
            notificationService.add(notification);
            return Result.success("通知已创建");
        }
    }
}
