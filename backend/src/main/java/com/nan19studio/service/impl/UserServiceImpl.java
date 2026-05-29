package com.nan19studio.service.impl;

import com.nan19studio.mapper.UserMapper;
import com.nan19studio.pojo.User;
import com.nan19studio.service.UserService;
import com.nan19studio.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password, String email, String phone, String nickname) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 注册
        userMapper.register(username, md5String, email, phone, nickname);
    }

    @Override
    public void updateLoginTime(Long userId) {
        userMapper.updateLoginTime(userId);
    }
}
