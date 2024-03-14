package com.test.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.test.spzx.model.dto.system.SysRoleDto;
import com.test.spzx.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {

    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Integer role_id);
    //查询所有角色
    Map<String, Object> findAll(Long userId);
}
