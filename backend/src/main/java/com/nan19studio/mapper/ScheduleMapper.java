package com.nan19studio.mapper;

import com.nan19studio.pojo.Schedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface ScheduleMapper {

    // 添加日程
    @Insert("insert into schedule (user_id, title, description, start_time, end_time, reminder_time, create_time, update_time) " +
            "values (#{id}, #{title}, #{description}, #{startTime}, #{endTime}, #{reminderTime}, now(), now())")
    void add(Integer id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime reminderTime);

    // 根据用户id查询日程
    @Select("select * from schedule where user_id = #{id} order by start_time desc")
    Schedule findByUserId(Integer id);

    // 根据用户id和日期查询日程
    @Select("select * from schedule where user_id = #{id} and DATE(start_time) = #{specific} order by start_time desc")
    Schedule findBySpecificDay(Integer id, String specific);
}
