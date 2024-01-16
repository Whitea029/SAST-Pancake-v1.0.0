package com.whitea.sastpancake.controller;

import com.whitea.dto.UserLoginDTO;
import com.whitea.entity.User;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.UserService;
import com.whitea.sastpancake.utils.JwtUtil;
import com.whitea.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        // TODO 该部分代码应该放在service层
        // 登录成功生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        //Long userId = BaseContext.getCurrentId();
        String userId = user.getId().toString();
        redisTemplate.opsForValue().set(userId,token);

        // 构造返回对象
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .token(token)
                .build();

        return Result.success(userLoginVO);

    }
}
