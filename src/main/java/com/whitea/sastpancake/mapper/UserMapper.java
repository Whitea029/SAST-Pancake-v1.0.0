package com.whitea.sastpancake.mapper;

import com.whitea.sastpancake.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {



    /**
     * 通过username查询
     * @return
     */
    User getByUsername(String username);
}
