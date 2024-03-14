package com.test.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.test.spzx.common.log.annotation.Log;
import com.test.spzx.common.log.enums.OperatorType;
import com.test.spzx.manager.service.BrandService;
import com.test.spzx.model.entity.product.Brand;
import com.test.spzx.model.entity.product.CategoryBrand;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService ;
    @Log(title = "品牌列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/{page}/{limit}")
    public Result getBrandList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
        //分页查询品牌列表
        PageInfo<Brand>pageInfo=brandService.findByPage(page,limit);
        //返回分页查询结果
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    @PostMapping("save")
    public Result save(@RequestBody Brand brand) {
        //保存品牌
        brandService.save(brand);
        //返回保存结果
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand) {
        //更新品牌
        brandService.updateById(brand);
        //返回更新结果
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        //删除品牌
        brandService.deleteById(id);
        //返回删除结果
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
//    查询所有品牌
    @GetMapping("/findAll")
    public Result findAll() {
        //查询所有品牌
        List<Brand> list = brandService.findAll();
        //返回查询结果
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }

//    //修改分类品牌
//    @PutMapping("/update")
//    public Result update(@RequestBody Brand brand) {
//        brandService.update(brand);
//        return Result.build(null,ResultCodeEnum.SUCCESS);
//    }
//    //删除分类品牌
//    @DeleteMapping("/delete/{id}")
//    public Result delete(@PathVariable Long id) {
//        brandService.delete(id);
//        return Result.build(null,ResultCodeEnum.SUCCESS);
//    }

}
