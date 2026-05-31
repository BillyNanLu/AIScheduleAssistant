package com.nan19studio.mapper;

import com.nan19studio.pojo.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    // 根据用户id查询通知
    @Select("select * from notification where user_id = #{id} order by create_time desc")
    List<Notification> findByUserId(Integer id);

    // 标记通知为已读
    @Update("update notification set is_read = 1 where id = #{id} and user_id = #{userId}")
    void markAsRead(@Param("id") Long id, @Param("userId") Integer userId);

    // 添加通知
    @Insert("insert into notification (user_id, schedule_id, title, content, type, create_time) " +
            "values (#{userId}, #{scheduleId}, #{title}, #{content}, #{type}, now())")
    void add(Notification notification);

    // 根据id查询通知
    @Select("select * from notification where schedule_id = #{id}")
    Notification findById(Long id);
}
