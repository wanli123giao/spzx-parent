package com.test.spzx.user.mapper;

import com.test.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    UserInfo selectByUsername(String username);

    void save(UserInfo userInfo);
}
