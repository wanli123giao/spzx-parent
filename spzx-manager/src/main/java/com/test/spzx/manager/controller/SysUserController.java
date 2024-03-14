package com.test.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.test.spzx.manager.service.SysUserService;
import com.test.spzx.model.dto.system.AssginRoleDto;
import com.test.spzx.model.dto.system.SysUserDto;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    //1.用户分页查询
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result  findByPage(@PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize,
                                SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo =sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);

    }
    //2.用户添加
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);

    }

    //3.用户修改
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }

    //4.用户删除
    @DeleteMapping(value = "/deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") Long userId) {
        sysUserService.deleteById(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //用户分配角色保存分配数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
