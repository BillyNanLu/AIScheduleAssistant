package com.nan19studio.service;

import com.nan19studio.pojo.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

    // TODO: 添加日程
    void add(String title, String description, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime reminderTime);

    // TODO: 获取日程列表（当前用户全部日程）
    List<Schedule> getScheduleList(Integer id);

    // TODO: 获取特定日期的日程列表（当前用户今日日程）
    Schedule getSpecificScheduleList(String specific);

    // TODO: 删除日程
    void deleteSchedule(Long id, Integer userId);
}
