package com.whitea.sastpancake.service;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;

public interface UserService {
    /**
     * 用户登录
     */
    void login(UserLoginDTO userLoginDTO);
}
