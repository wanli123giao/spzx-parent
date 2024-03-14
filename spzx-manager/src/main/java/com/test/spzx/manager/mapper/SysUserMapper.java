package com.test.spzx.manager.mapper;

import com.test.spzx.model.dto.system.SysUserDto;
import com.test.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
SysUser selectUserInfoByUserName(String userName);
    //1.用户分页查询
    List<SysUser> findByPage(SysUserDto sysUserDto);
    //2.用户添加
    void save(SysUser sysUser);
    //3.用户修改
    void update(SysUser sysUser);
    //4.用户删除
    void delete(Long userId);
}