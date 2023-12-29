package com.whitea.sastpancake.controller;

import com.whitea.sastpancake.context.BaseContext;
import com.whitea.sastpancake.entity.dto.PancakeDTO;
import com.whitea.sastpancake.entity.dto.UserLoginDTO;
import com.whitea.sastpancake.entity.po.Pancake;
import com.whitea.sastpancake.entity.po.User;
import com.whitea.sastpancake.entity.vo.UserLoginVO;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.PancakeService;
import com.whitea.sastpancake.service.UserService;
import com.whitea.sastpancake.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class Controller {


    @Autowired
    private PancakeService pancakeService;
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
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

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

    /**
     * 查询所有的饼
     * @return
     */
    @GetMapping("/pancake")
    public Result<List<Pancake>> pancakeQuery() {
        log.info("查询所有的饼");
        List<Pancake> pancakes = pancakeService.pancakeQuery();
        return Result.success(pancakes);
    }

    /**
     * 做饼
     * @return
     */
    @PutMapping("/pancake")
    public Result makePancake(@RequestBody PancakeDTO pancakeDTO) {
        log.info("做饼:{}", pancakeDTO);
        pancakeService.makePancake(pancakeDTO);
        return Result.success();
    }

    /**
     * 做锅
     * @param pancakeId
     * @return
     */
    @PostMapping("/pancake/{pancake_id}")
    public Result makePot(@PathVariable("pancake_id") Integer pancakeId) {
        log.info("做锅：{}", pancakeId);
        pancakeService.makePot(pancakeId);
        return Result.success();
    }

    /**
     * 删除饼
     * @return
     */
    @DeleteMapping("/pancake/{pancake_id}")
    public Result deletePancake(@PathVariable("pancake_id") Integer pancakeId, HttpServletRequest request) {
        // 解决用户校验问题
        // 方法一：解析http请求头部传递的token
        //String token = request.getHeader(jwtProperties.getAdminTokenName());
        // 方法二：使用redis，去redis中用id对应查找
        String userId = BaseContext.getCurrentId().toString();
        String token = redisTemplate.opsForValue().get(userId).toString();
        log.info("删除饼:{}", pancakeId);
        pancakeService.deletePancake(pancakeId, token);
        return Result.success();
    }
}
