package com.whitea.sastpancake.service;


import com.whitea.dto.UserLoginDTO;
import com.whitea.entity.User;

public interface UserService {
    /**
     * 用户登录
     *
     * @return
     */
    User login(UserLoginDTO userLoginDTO);
}
