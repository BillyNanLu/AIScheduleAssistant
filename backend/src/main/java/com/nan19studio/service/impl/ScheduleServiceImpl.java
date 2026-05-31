package com.nan19studio.service.impl;

import com.nan19studio.mapper.ScheduleMapper;
import com.nan19studio.pojo.Schedule;
import com.nan19studio.service.ScheduleService;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<Schedule> getScheduleList(Integer id) {
        return scheduleMapper.findByUserId(id);
    }

    @Override
    public List<Schedule> getSpecificScheduleList(Integer id, String specific) {
        return scheduleMapper.findBySpecificDay(id, specific);
    }

    @Override
    public void deleteSchedule(Long id, Integer userId) {
        scheduleMapper.deleteById(id, userId);
    }

    @Override
    public void updateSchedule(Schedule schedule, Integer userId) {
        scheduleMapper.update(schedule, userId);
    }

    @Override
    public void updateScheduleStatus(Long id, Integer status, Integer userId) {
        scheduleMapper.updateStatus(id, status, userId);
    }
}
