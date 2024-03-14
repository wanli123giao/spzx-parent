package com.test.spzx.manager.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.spzx.common.exception.GuiguException;
import com.test.spzx.manager.mapper.SysRoleUserMapper;
import com.test.spzx.manager.mapper.SysUserMapper;
import com.test.spzx.manager.service.SysUserService;
import com.test.spzx.model.dto.system.AssginRoleDto;
import com.test.spzx.model.dto.system.LoginDto;
import com.test.spzx.model.dto.system.SysUserDto;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper ;
//用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        // 校验验证码是否正确
        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();     // redis中验证码的数据key

        // 从Redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR) ;
        }

        // 验证通过删除redis中的验证码
        redisTemplate.delete("user:login:validatecode:" + codeKey) ;

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(loginDto.getUserName());
        if(sysUser == null) {
//            throw new RuntimeException("用户名或者密码错误") ;
            throw new GuiguException((ResultCodeEnum.LOGIN_ERROR) );
        }

        // 验证密码是否正确
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //DigestUtils.md5DigestAsHex()方法将输入的密码转换为MD5哈希值的十六进制表示形式。
        // inputPassword.getBytes()方法将inputPassword字符串转换为字节数组
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            throw new RuntimeException("用户名或者密码错误") ;
        }

        // 生成令牌，保存数据到Redis中
        //使用Java的UUID类生成一个随机的UUID，并将其转换为字符串。toString()方法将UUID转换为字符串，
        // replace("-", "")方法将字符串中的"-"替换为空字符串，以获得一个没有分隔符的字符串
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }
//获取用户信息
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson , SysUser.class) ;
    }
//用户退出
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    //1.用户分页查询
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        //调用sysUserMapper的findByPage方法，传入sysUserDto参数，获取分页数据
        List<SysUser> list=sysUserMapper.findByPage(sysUserDto);
        //将分页数据封装到PageInfo对象中
        PageInfo<SysUser> pageInfo=new PageInfo<>(list);
        //返回PageInfo对象
        return pageInfo;
    }


    //2.用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        //判断用户是否存在
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser !=null ){
//            throw new RuntimeException("用户名已存在");
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //输入密码加密 通过md5加密
        String md5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5_password);

//将Status的状态设置为1 1代表可用 0不可用
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }
    //3.用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }
    //4.用户删除
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }
    //用户分配角色保存分配数据
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId()) ;

        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId:roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }

    }

}