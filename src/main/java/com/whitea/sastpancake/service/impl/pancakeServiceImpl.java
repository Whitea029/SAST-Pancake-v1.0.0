package com.whitea.sastpancake.service.impl;

import com.whitea.sastpancake.entity.dto.PancakeDTO;
import com.whitea.sastpancake.entity.po.Pancake;
import com.whitea.sastpancake.entity.po.User;
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
     * @param token
     */
    @Override
    public void deletePancake(Integer pancakeId, String token) {
        // 校验用户身份
        Map<String, Object> claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
        Integer role = Integer.valueOf(claims.get("role").toString());
        if (role == 0) {
            throw new NoPermissionException("非管理员，无删除权限");
        }
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
