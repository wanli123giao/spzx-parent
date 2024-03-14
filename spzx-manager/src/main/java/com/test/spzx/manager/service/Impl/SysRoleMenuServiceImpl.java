package com.test.spzx.manager.service.Impl;

import com.test.spzx.manager.mapper.SysRoleMenuMapper;
import com.test.spzx.manager.service.SysMenuService;
import com.test.spzx.manager.service.SysRoleMenuService;
import com.test.spzx.model.dto.system.AssginMenuDto;
import com.test.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    //1.查询所有菜单,和角色分配的菜单
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        //查询角色分配菜单id列表
        List<Long> roleMenuIds=sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        Map<String,Object> map=new HashMap<>();
        map.put("sysMenuList",sysMenuList);
        map.put("roleMenuIds",roleMenuIds);
                return map;
    }

    //分配菜单
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        // 根据角色的id删除其所对应的菜单数据
        sysRoleMenuMapper.deleteSysRoleMenuByRoleId(assginMenuDto.getRoleId());
        //保存分配数据
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if (menuInfo != null && menuInfo.size() > 0){
            sysRoleMenuMapper.doAssign(assginMenuDto);

        }

    }


}
