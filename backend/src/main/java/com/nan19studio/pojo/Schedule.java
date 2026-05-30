package com.nan19studio.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private Long id;

    private Long userId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private LocalDateTime reminderTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
