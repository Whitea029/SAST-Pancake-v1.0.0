package com.whitea.sastpancake;

import com.whitea.entity.User;
import com.whitea.sastpancake.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testGetAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);

    }

}
