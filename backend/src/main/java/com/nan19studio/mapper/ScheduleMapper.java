package com.nan19studio.mapper;

import com.nan19studio.pojo.Schedule;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    // 添加日程
    @Insert("insert into schedule (user_id, title, description, start_time, end_time, reminder_time, create_time, update_time) " +
            "values (#{id}, #{title}, #{description}, #{startTime}, #{endTime}, #{reminderTime}, now(), now())")
    void add(Integer id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime reminderTime);

    // 根据用户id查询日程
    @Select("select * from schedule where user_id = #{id} order by start_time desc")
    List<Schedule> findByUserId(Integer id);

    // 根据用户id和日期查询日程
    @Select("select * from schedule where user_id = #{id} and DATE(start_time) = #{specific} order by start_time desc")
    Schedule findBySpecificDay(Integer id, String specific);

    // 根据日程id和用户id删除日程
    @Delete("delete from schedule where id = #{id} and user_id = #{userId}")
    void deleteById(Long id, Integer userId);

    // 根据日程id和用户id更新日程
    @Update("update schedule set title = #{schedule.title}, description = #{schedule.description}, start_time = #{schedule.startTime}, end_time = #{schedule.endTime}, reminder_time = #{schedule.reminderTime} ,update_time = now() where id = #{schedule.id} and user_id = #{userId}")
    void update(Schedule schedule, Integer userId);

    // 根据日程id和用户id更新日程状态
    @Update("update schedule set status=#{status}, update_time=now() where id=#{id} and user_id=#{userId}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("userId") Integer userId);
}
