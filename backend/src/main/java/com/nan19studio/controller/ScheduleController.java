package com.nan19studio.controller;

import com.nan19studio.pojo.Result;
import com.nan19studio.pojo.Schedule;
import com.nan19studio.service.ScheduleService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
// TODO: Implement schedule controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // TODO: Add schedule
    @PostMapping("/add")
    public Result add(String title,
                      String description,
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime reminderTime) {
        scheduleService.add(title, description, startTime, endTime, reminderTime);

        return Result.success("添加成功");
    }

}
