package com.nan19studio.mapper;

import com.nan19studio.pojo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    // 根据用户id查询通知
    @Select("select * from notification where user_id = #{id} order by create_time desc")
    List<Notification> findByUserId(Integer id);
}
