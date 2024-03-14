package com.test.spzx.user.service;

import com.test.spzx.model.dto.h5.UserLoginDto;
import com.test.spzx.model.dto.h5.UserRegisterDto;
import com.test.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
