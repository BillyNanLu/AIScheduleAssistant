package com.nan19studio.controller;

import com.nan19studio.pojo.Result;
import com.nan19studio.pojo.Schedule;
import com.nan19studio.service.ScheduleService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    // TODO: Get schedule list（当前用户全部日程）
    @GetMapping("/list")
    public Result<List<Schedule>> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Schedule> schedules = scheduleService.getScheduleList(id);
        return Result.success(schedules);
    }

    // TODO: Get specific day's schedule （当前用户今日日程）
    @GetMapping("/specific")
    public Result<Schedule> specific(String specific) {
        Schedule schedule = scheduleService.getSpecificScheduleList(specific);
        return Result.success(schedule);
    }

    // TODO: Delete schedule
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        scheduleService.deleteSchedule(id, userId);
        return Result.success("删除成功");
    }

}
