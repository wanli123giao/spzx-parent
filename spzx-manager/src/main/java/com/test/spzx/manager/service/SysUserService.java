package com.test.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.test.spzx.model.dto.system.AssginRoleDto;
import com.test.spzx.model.dto.system.LoginDto;
import com.test.spzx.model.dto.system.SysUserDto;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    //用户登录
    LoginVo login(LoginDto loginDto) ;
//获取用户当前信息
    SysUser getUserInfo(String token);
//用户退出
    void logout(String token);
//1.用户条件分页查询接口
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);
    //2.用户添加
    void saveSysUser(SysUser sysUser);
    //3.用户修改
    void updateSysUser(SysUser sysUser);
    //4.用户删除
    void deleteById(Long userId);
    //用户分配角色保存分配数据

    void doAssign(AssginRoleDto assginRoleDto);
}
