package com.test.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.test.spzx.common.log.annotation.Log;
import com.test.spzx.manager.service.SysRoleService;
import com.test.spzx.model.dto.system.SysRoleDto;
import com.test.spzx.model.entity.system.SysRole;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    //查询所有角色
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId){
        Map<String, Object> resultMap = sysRoleService.findAll(userId);
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }


    //角色删除
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteSysRole(@PathVariable("roleId") Integer role_id){
        sysRoleService.deleteById(role_id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //角色修改
    @PutMapping(value = "/updateSysRole")
    public Result uodateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //角色添加
    @Log(title = "角色添加",businessType = 1) //添加Log注解，设置属性
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //角色列表方法
    //current 当前页 limit 每页显示记录数
    //SysRoleDto 条件角色名称对象
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current")Integer current,
                             @PathVariable("limit")Integer limit,
                             @RequestBody SysRoleDto sysRoleDto){
//用JSON进行数据传输
        //pageHelper实现分页
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, current ,limit) ;
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

}
