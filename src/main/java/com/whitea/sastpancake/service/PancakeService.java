package com.whitea.sastpancake.service;

import com.whitea.sastpancake.entity.dto.PancakeDTO;
import com.whitea.sastpancake.entity.po.Pancake;

import java.util.List;

public interface PancakeService {
    /**
     * 查询所有的饼
     * @return
     */
    List<Pancake> pancakeQuery();

    /**
     * 做饼
     * @param pancakeDTO
     */
    void makePancake(PancakeDTO pancakeDTO);

    /**
     * 删除饼
     * @param pancakeId
     */
    void deletePancake(Integer pancakeId);

    /**
     * 做锅
     * @param pancakeId
     */
    void makePot(Integer pancakeId);
}
