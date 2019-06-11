package com.example.zebra.sharding.demo;

import com.example.zebra.sharding.demo.entities.UserEntity;
import com.example.zebra.sharding.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ZebraShardingDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testInsertUser() throws Exception {
        UserEntity userInfo = new UserEntity();
        userInfo.setUid("uid0000003");
        userInfo.setName("test003");
        userMapper.insertUserInfo(userInfo);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        UserEntity userInfo = userMapper.getUserInfoById("uid0000001");
        System.out.println(userInfo);
    }

}
