package com.whitea.sastpancake.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whitea.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {



    /**
     * 通过username查询
     * @return
     */
    User getByUsername(String username);

    /**
     * 根据userId查询
     * @return
     */
    User getByUserId(Long userId);
}
