package com.nan19studio.service.impl;

import com.nan19studio.mapper.ScheduleMapper;
import com.nan19studio.pojo.Schedule;
import com.nan19studio.service.ScheduleService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public void add(String title, String description, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime reminderTime) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        scheduleMapper.add(id, title, description, startTime, endTime, reminderTime);
    }

    @Override
    public Schedule getScheduleList(Integer id) {
        Schedule schedule = scheduleMapper.findByUserId(id);
        return schedule;
    }
}
