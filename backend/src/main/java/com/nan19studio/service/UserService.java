package com.nan19studio.service;

import com.nan19studio.pojo.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // 根据用户名查询用户
    User findByUsername(@Pattern(regexp = "^\\S{5,16}$") String username);

    // 注册
    void register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password, String email, String phone, String nickname);

    // 更新登录时间
    void updateLoginTime(@NotNull Long userId);

    // 更新用户信息
    void updateInfo(User user);

    // 更新密码
    void updatePwd(String newPwd);

    // 更新头像
    String updateAvatar(MultipartFile file);
}
