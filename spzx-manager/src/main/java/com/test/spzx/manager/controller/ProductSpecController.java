package com.test.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.test.spzx.manager.service.ProductSpecService;
import com.test.spzx.model.entity.product.ProductSpec;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/productSpec")

public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService ;
    @GetMapping("findAll")

    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
    //列表查询
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit){
        PageInfo<ProductSpec> pageInfo=productSpecService.findByPage(page,limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }
    //添加
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;

    }
    //修改
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;

    }
    //删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productSpecService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
