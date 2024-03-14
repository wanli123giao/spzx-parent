package com.test.spzx.product.controller;

import com.test.spzx.model.entity.product.Category;
import com.test.spzx.model.entity.product.ProductSku;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.h5.IndexVo;
import com.test.spzx.product.service.CategoryService;
import com.test.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
//@SuppressWarnings({"unchecked", "rawtypes"})
//@CrossOrigin
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result index(){
        //获取一级分类
       List<Category> categoryList= categoryService.selectOneCategory();
        //根骨销量获取前10条记录
        List<ProductSku>productSkuList=productService.selectProductSkuBySale();
        IndexVo indexVo = new IndexVo() ;
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo , ResultCodeEnum.SUCCESS);
    }
}
