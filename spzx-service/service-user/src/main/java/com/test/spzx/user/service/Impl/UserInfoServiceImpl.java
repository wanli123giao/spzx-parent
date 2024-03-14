package com.test.spzx.user.service.Impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.test.spzx.common.exception.GuiguException;
import com.test.spzx.model.dto.h5.UserLoginDto;
import com.test.spzx.model.dto.h5.UserRegisterDto;
import com.test.spzx.model.entity.user.UserInfo;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.h5.UserInfoVo;
import com.test.spzx.user.mapper.UserInfoMapper;
import com.test.spzx.user.service.UserInfoService;
import com.test.spzx.util.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(UserRegisterDto userRegisterDto) {

        //1 userRegisterDto获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //2 验证码校验
        //2.1 从redis获取发送验证码
        String redisCode = redisTemplate.opsForValue().get("phone:code:" + username);
        //2.2 获取输入的验证码，进行比对
        if (!redisCode.equals(code)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //3 校验用户名不能重复
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo != null) { //存在相同用户名
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }//保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);

// 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        //1.获取用户名密码
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if(null == userInfo) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        String database_password = userInfo.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!md5_password.equals(userInfo.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
//        String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
//        if(StringUtils.isEmpty(userInfoJSON)) {
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH) ;
//        }
//        UserInfo userInfo = JSON.parseObject(userInfoJSON , UserInfo.class) ;
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo ;
    }

}