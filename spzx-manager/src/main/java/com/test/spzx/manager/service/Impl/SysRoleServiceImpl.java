package com.test.spzx.manager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.spzx.manager.mapper.SysRoleMapper;
import com.test.spzx.manager.mapper.SysRoleUserMapper;
import com.test.spzx.manager.service.SysRoleService;
import com.test.spzx.model.dto.system.SysRoleDto;
import com.test.spzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current, limit);

        List<SysRole> list=sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo(list) ;
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void deleteById(Integer role_id) {
        sysRoleMapper.delete(role_id);
    }
    //查询所有角色
    @Override
    public Map<String, Object> findAll(Long userId) {
        //查询所有角色,分配过的角色列表
        List<SysRole> sysRoleList = sysRoleMapper.findAll() ;
        //
        //根据userid查询用户分配过的id列表
        List<Long> sysRoles = sysRoleUserMapper.findSysUserRoleByUserId(userId);
        Map<String , Object> map = new HashMap<>() ;
        map.put("allRolesList" , sysRoleList) ;
        map.put("sysUserRoles", sysRoles);
        return map;

    }
}
