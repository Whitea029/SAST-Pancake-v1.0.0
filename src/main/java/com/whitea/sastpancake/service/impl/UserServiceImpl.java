package com.whitea.sastpancake.service.impl;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.entity.po.User;
import com.whitea.sastpancake.mapper.UserMapper;
import com.whitea.sastpancake.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    @Override
    public void login(UserLoginDTO userLoginDTO) {
        User user = new User();

        BeanUtils.copyProperties(userLoginDTO, user);

        userMapper.login(user);
    }
}
