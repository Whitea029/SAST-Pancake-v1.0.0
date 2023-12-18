package com.whitea.sastpancake.controller.login;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO);
        userService.login(userLoginDTO);
        return Result.success();
    }

}
