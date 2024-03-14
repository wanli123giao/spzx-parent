package com.test.spzx.manager.service.Impl;

import com.test.spzx.common.exception.GuiguException;
import com.test.spzx.manager.mapper.SysMenuMapper;
import com.test.spzx.manager.mapper.SysRoleMenuMapper;
import com.test.spzx.manager.service.SysMenuService;
import com.test.spzx.manager.service.SysRoleMenuService;
import com.test.spzx.manager.utils.MenuHelper;
import com.test.spzx.model.entity.system.SysMenu;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.system.SysMenuVo;
import com.test.spzx.util.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表
    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.findAll();
        if (CollectionUtils.isEmpty(sysMenuList)) return null;
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList); //构建树形数据
        return treeList;
    }

    //菜单添加
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenu(sysMenu);
    }

    private void updateSysRoleMenu(SysMenu sysMenu) {
        SysMenu parentMenu=sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu!=null){
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            // 递归调用
            updateSysRoleMenu(parentMenu) ;

        }
    }

    //菜单修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }
//菜单删除

    @Override
    public void removeById(Long id) {
        //判断当前菜单id，查询收否包含子节点（子菜单）
        int count = sysMenuMapper.selectCountById(id);
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.removeById(id);
    }
//查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();          // 获取当前登录用户的id

        // 获取当前用户菜单列表
        List<SysMenu> sysMenuList = sysMenuMapper.findMenusByUserId(userId) ;

        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);
    }
    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}

