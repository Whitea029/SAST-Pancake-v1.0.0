package com.whitea.sastpancake.controller.login;

import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.entity.po.User;
import com.whitea.sastpancake.entity.vo.UserLoginVO;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.UserService;
import com.whitea.sastpancake.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        // 登录成功生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        // 构造返回对象
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .token(token)
                .build();

        return Result.success(userLoginVO);

    }

}
