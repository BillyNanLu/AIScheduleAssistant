package com.nan19studio.mapper;

import com.nan19studio.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    // 注册新用户
    @Insert("insert into user(username, password, email, phone, nickname, create_time, update_time, last_login) " +
            "values(#{username}, #{md5String}, #{email}, #{phone}, #{nickname}, now(), now(), now())")
    void register(String username, String md5String, String email, String phone, String nickname);

    // 更新用户登录时间
    @Update("update user set last_login=now() where id=#{userId}")
    void updateLoginTime(Long userId);
}
