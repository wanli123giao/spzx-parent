package com.test.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    //根据用户ID删除用户
    void deleteByUserId(Long userId);

    //根据用户ID分配角色
    void doAssign(Long userId, Long roleId);
    //根据userid查询用户分配过的id列表
    List<Long> findSysUserRoleByUserId(Long userId);
}
