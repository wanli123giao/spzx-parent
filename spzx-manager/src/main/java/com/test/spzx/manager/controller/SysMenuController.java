package com.test.spzx.manager.controller;

import com.test.spzx.manager.service.SysMenuService;
import com.test.spzx.model.entity.system.SysMenu;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜单列表
    @GetMapping(value = "/findNodes")
    public Result findNodes() {
        List<SysMenu> list= sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    //菜单添加
    @PostMapping(value = "/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //菜单修改
    @PutMapping (value = "/updateById")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
//菜单删除
    @DeleteMapping(value = "/removeById/{id}")
    public Result removeById(@PathVariable("id") Long id){
        sysMenuService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
