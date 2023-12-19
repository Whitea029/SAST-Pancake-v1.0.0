package com.whitea.sastpancake.service;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.entity.po.User;

public interface UserService {
    /**
     * 用户登录
     *
     * @return
     */
    User login(UserLoginDTO userLoginDTO);
}
