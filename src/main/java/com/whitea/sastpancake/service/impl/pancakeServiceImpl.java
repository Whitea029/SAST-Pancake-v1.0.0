package com.whitea.sastpancake.service.impl;

import com.whitea.sastpancake.entity.dto.PancakeDTO;
import com.whitea.sastpancake.entity.po.Pancake;
import com.whitea.sastpancake.mapper.PancakeMapper;
import com.whitea.sastpancake.service.PancakeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class pancakeServiceImpl implements PancakeService {

    @Autowired
    private PancakeMapper pancakeMapper;


    /**
     * 查询所有的饼
     * @return
     */
    @Override
    public List<Pancake> pancakeQuery() {
        List<Pancake> list = pancakeMapper.query();
        return list;
    }

    /**
     * 做饼
     *
     * @param pancakeDTO
     */
    @Override
    public void makePancake(PancakeDTO pancakeDTO) {
        Pancake pancake = new Pancake();

        BeanUtils.copyProperties(pancakeDTO, pancake);
        pancake.setCreateTime(LocalDateTime.now());

        pancakeMapper.save(pancake);
    }

    /**
     * 删除饼
     * @param pancakeId
     */
    @Override
    public void deletePancake(Integer pancakeId) {
        pancakeMapper.deleteById(pancakeId);
    }
}
