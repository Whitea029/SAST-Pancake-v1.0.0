package com.whitea.sastpancake.controller.user;

import com.whitea.sastpancake.entity.dto.PancakeDTO;
import com.whitea.sastpancake.entity.po.Pancake;
import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.PancakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j

public class UserController {

    @Autowired
    private PancakeService pancakeService;


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




}
