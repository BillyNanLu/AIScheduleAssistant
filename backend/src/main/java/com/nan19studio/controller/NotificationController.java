package com.nan19studio.controller;

import com.nan19studio.pojo.Notification;
import com.nan19studio.pojo.Result;
import com.nan19studio.service.NotificationService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // TODO: Get notification list（当前用户全部通知）
    @GetMapping
    public Result<List<Notification>> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Notification> notifications = notificationService.getNotificationList(id);
        return Result.success(notifications);
    }

}
