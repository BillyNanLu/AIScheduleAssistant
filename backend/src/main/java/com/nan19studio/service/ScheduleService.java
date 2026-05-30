package com.nan19studio.service;

import com.nan19studio.pojo.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface ScheduleService {

    // TODO: 添加日程
    void add(String title, String description, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime reminderTime);

}
