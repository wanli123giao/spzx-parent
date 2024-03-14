package com.test.spzx.manager.service;

import com.test.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    //1.查询所有菜单,和角色分配的菜单

    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
//分配菜单
    void doAssign(AssginMenuDto assginMenuDto);
}
