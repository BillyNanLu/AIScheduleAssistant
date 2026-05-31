package com.nan19studio.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    private Long id;

    private Long userId;

    private Long scheduleId;

    private String title;

    private String content;

    private String type;

    private Integer isRead;

    private LocalDateTime createTime;
}
