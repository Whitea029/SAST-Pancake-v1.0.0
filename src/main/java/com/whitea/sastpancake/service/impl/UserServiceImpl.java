package com.whitea.sastpancake.service.impl;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.entity.po.User;
import com.whitea.sastpancake.mapper.UserMapper;
import com.whitea.sastpancake.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);

        // 判断用户是否存在
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 密码对比
        // 密码MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            // 密码错误
            throw new RuntimeException("密码错误");
        }

        return user;
    }
}
