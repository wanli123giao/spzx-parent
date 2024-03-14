package com.test.spzx.manager.utils;

import com.test.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
   public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        // 创建一个空的SysMenu列表
        List<SysMenu> trees = new ArrayList<>();
        // 遍历sysMenuList
        for (SysMenu sysMenu : sysMenuList) {
            // 如果父ID为0，则添加到trees中
            if (sysMenu.getParentId().longValue() == 0) {
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        // 返回trees
        return trees;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {

        // 创建一个空的SysMenu列表
        sysMenu.setChildren(new ArrayList<>());
        // 遍历sysMenuList
        for(SysMenu it : sysMenuList) {
            // 如果当前SysMenu的父ID等于遍历的SysMenu的ID，则添加到当前SysMenu的子列表中
            if (sysMenu.getId().longValue() == it.getParentId().longValue()){
                sysMenu.getChildren().add(findChildren(it,sysMenuList));
            }
        }
        // 返回当前SysMenu
        return sysMenu;
    }
}