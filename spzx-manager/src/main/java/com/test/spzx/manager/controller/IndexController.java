package com.test.spzx.manager.controller;

import com.test.spzx.manager.service.SysMenuService;
import com.test.spzx.model.vo.system.SysMenuVo;
import com.test.spzx.util.AuthContextUtil;
import com.test.spzx.manager.service.SysUserService;
import com.test.spzx.manager.service.ValidateCodeService;
import com.test.spzx.model.dto.system.LoginDto;
import com.test.spzx.model.entity.system.SysUser;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.system.LoginVo;
import com.test.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;
    //查询用户可以操作的菜单
    @GetMapping(value = "menus")
    public Result menus(){
        List<SysMenuVo> list=sysMenuService.findMenusByUserId();
        return Result.build(list,ResultCodeEnum.SUCCESS) ;
    }
//用户退出
    @GetMapping(value = "logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS) ;

    }
//优化获取用户信息
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        return Result.build(AuthContextUtil.get()  , ResultCodeEnum.SUCCESS) ;
    }
    //获取用户信息
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        SysUser sysUser = sysUserService.getUserInfo(token) ;
//        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
//    }
    //验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }
    //用户登陆
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

}
