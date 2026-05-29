package com.nan19studio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Long id;                 // 主键ID

    private String username;         // 登录名

    @JsonIgnore // 让springmvc把当前对象转化成json字符串的时候，忽略password，最终的json字符串中就没有password这个属性了
    private String password;         // 密码（加密存储，MD5）

    private String avatar;           // 头像URL

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;             // 姓名（昵称）

    @NotEmpty
    @Email
    private String email;            // 邮箱
    private String phone;            // 电话
    private Integer role;            // 角色（1=用户, 0=管理员）
    private LocalDateTime createTime; // 注册时间
    private LocalDateTime updateTime; // 更新时间
    private LocalDateTime lastLogin;  // 最近登录时间
}