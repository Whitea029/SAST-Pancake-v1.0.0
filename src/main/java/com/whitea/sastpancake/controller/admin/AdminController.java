package com.whitea.sastpancake.controller.admin;

import com.whitea.sastpancake.result.Result;
import com.whitea.sastpancake.service.PancakeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private PancakeService pancakeService;


    /**
     * 删除饼
     * @return
     */
    @DeleteMapping("/pancake/{pancake_id}")
    public Result deletePancake(@PathVariable Integer pancake_id) {
        log.info("删除饼:{}", pancake_id);
        pancakeService.deletePancake(pancake_id);
        return Result.success();
    }
}
