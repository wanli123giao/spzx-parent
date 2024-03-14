package com.test.spzx.manager.controller;

import com.test.spzx.manager.service.ProductUnitService;
import com.test.spzx.model.entity.base.ProductUnit;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;

    @GetMapping("findAll")
    public Result findAll(){
        List<ProductUnit>productUnitList=productUnitService.findAll();
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }
}
