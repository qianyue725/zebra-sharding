package com.example.zebra.sharding.demo.mapper;

import com.example.zebra.sharding.demo.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-06 16:10
 */
@Mapper
public interface UserMapper {

    UserEntity getUserInfoById(String uid) throws Exception;

    void insertUserInfo(UserEntity userEntity) throws Exception;

}