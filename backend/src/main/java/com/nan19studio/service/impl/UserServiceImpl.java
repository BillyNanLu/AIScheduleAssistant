package com.nan19studio.service.impl;

import com.nan19studio.mapper.UserMapper;
import com.nan19studio.pojo.User;
import com.nan19studio.service.UserService;
import com.nan19studio.utils.Md5Util;
import com.nan19studio.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    @Override
    public void updateInfo(User user) {
        userMapper.updateInfo(user);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        // 存储目录（项目根目录 /uploads/）
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 文件名：时间戳 + 原始名
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败");
        }

        // 存入数据库的路径（例如：/uploads/xxx.png）
        String avatarPath = "/uploads/" + fileName;

        userMapper.updateAvatar(avatarPath, id);

        return avatarPath;
    }
}
