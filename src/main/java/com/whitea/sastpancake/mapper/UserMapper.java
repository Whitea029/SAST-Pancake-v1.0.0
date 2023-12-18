package com.whitea.sastpancake.mapper;

import com.whitea.sastpancake.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    /**
     * PancakeMapper.xml
     * @param user
     */
    void login(User user);
}
