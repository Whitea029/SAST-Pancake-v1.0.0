package com.whitea.sastpancake.controller;

import com.whitea.dto.PancakeDTO;
import com.whitea.entity.Pancake;
import com.whitea.sastpancake.context.BaseContext;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.PancakeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pancake")
public class PancakeController {


    @Autowired
    private PancakeService pancakeService;
    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 查询所有的饼
     * @return
     */
    @GetMapping
    public Result<List<Pancake>> pancakeQuery() {
        log.info("查询所有的饼");
        List<Pancake> pancakes = pancakeService.pancakeQuery();
        return Result.success(pancakes);
    }

    /**
     * 做饼
     * @return
     */
    @PutMapping
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
    @PostMapping("/{pancake_id}")
    public Result makePot(@PathVariable("pancake_id") Integer pancakeId) {
        log.info("做锅：{}", pancakeId);
        pancakeService.makePot(pancakeId);
        return Result.success();
    }

    /**
     * 删除饼
     * @return
     */
    @DeleteMapping("/{pancake_id}")
    public Result deletePancake(@PathVariable("pancake_id") Integer pancakeId, HttpServletRequest request) {
        // 解决用户校验问题
        // 方法一：解析http请求头部传递的token
        //String token = request.getHeader(jwtProperties.getAdminTokenName());
        // 方法二：使用redis，去redis中用id对应查找
        //String userId = BaseContext.getCurrentId().toString();
        //String token = redisTemplate.opsForValue().get(userId).toString();
        log.info("删除饼:{}", pancakeId);
        pancakeService.deletePancake(pancakeId);
        return Result.success();
    }
}
