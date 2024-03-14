package com.test.spzx.manager.mapper;

import com.test.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    //1.查询所有菜单,和角色分配的菜单
    List<Long> findSysRoleMenuByRoleId(Long roleId);
    // 根据角色的id删除其所对应的菜单数据
    void deleteSysRoleMenuByRoleId(Long roleId);
    //保存分配数据
    void doAssign(AssginMenuDto assginMenuDto);
    // 根据id更新sys_role_menu表中父节点is_half字段
void updateSysRoleMenuIsHalf(Long id);
}
