package com.whitea.sastpancake.service;


import com.whitea.dto.PancakeDTO;
import com.whitea.entity.Pancake;

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
     *
     * @param pancakeId
     * @param token
     */
    void deletePancake(Integer pancakeId);

    /**
     * 做锅
     * @param pancakeId
     */
    void makePot(Integer pancakeId);
}
