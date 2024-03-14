package com.test.spzx.manager.mapper;

import com.test.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    //菜单列表
    List<SysMenu> findAll();
    //菜单添加
    void save(SysMenu sysMenu);
    //菜单修改
    void update(SysMenu sysMenu);
//菜单删除

    void removeById(Long id);

    int selectCountById(Long id);

//查询用户可以操作的菜单
    List<SysMenu> findMenusByUserId(Long userId);
    // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
    SysMenu selectParentMenu(Long parentId);
}
