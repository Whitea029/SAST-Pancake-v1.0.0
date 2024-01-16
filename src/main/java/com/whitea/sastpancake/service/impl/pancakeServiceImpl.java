package com.whitea.sastpancake.service.impl;
import com.whitea.dto.PancakeDTO;
import com.whitea.entity.Pancake;
import com.whitea.sastpancake.esception.NoPermissionException;
import com.whitea.sastpancake.esception.PancakeNotFoundException;
import com.whitea.sastpancake.esception.PancakeStatusException;
import com.whitea.sastpancake.mapper.PancakeMapper;
import com.whitea.sastpancake.mapper.UserMapper;
import com.whitea.sastpancake.properties.JwtProperties;
import com.whitea.sastpancake.service.PancakeService;
import com.whitea.sastpancake.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class pancakeServiceImpl implements PancakeService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

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
     *
     * @param pancakeId
     */
    @Override
    public void deletePancake(Integer pancakeId) {
        pancakeMapper.deleteById(pancakeId);
    }

    /**
     * 做锅
     *
     * @param pancakeId
     */
    @Override
    public void makePot(Integer pancakeId) {
        Pancake pancake = pancakeMapper.getByPancakeId(pancakeId);

        if (pancake == null) {
            throw new PancakeNotFoundException("没有找到该饼");
        }

        if (pancake.getIsDone() == 1) {
            throw new PancakeStatusException("该饼的锅已经做好");
        }

        pancakeMapper.updateWithIsDown(pancakeId);
    }
}
