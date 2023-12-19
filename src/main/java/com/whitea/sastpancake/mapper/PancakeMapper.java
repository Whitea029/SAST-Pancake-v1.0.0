package com.whitea.sastpancake.mapper;

import com.whitea.sastpancake.entity.po.Pancake;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PancakeMapper {

    /**
     * 查询所有的饼
     * @return
     */
    List<Pancake> query();

    /**
     * 做饼
     * @param pancake
     */
    void save(Pancake pancake);

    /**
     * 删除饼
     */
    void deleteById(Integer pancakeId);


    /**
     * 根据id查询饼
     * @param pancakeId
     */
    Pancake getByPancakeId(Integer pancakeId);

    /**
     * 做锅
     * @param pancakeId
     */
    void updateWithIsDown(Integer pancakeId);
}
