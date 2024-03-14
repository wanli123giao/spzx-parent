package com.test.spzx.manager.controller;

import com.test.spzx.manager.service.CategoryService;
import com.test.spzx.model.entity.product.Category;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
//懒加载
    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findCategoryList/{id}")
    public Result<List<Category>> findCategoryList(@PathVariable Long id) {
        List<Category> list = categoryService.findByParentId(id);
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
    //导出
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }
    //导入
    @PostMapping("/importData")
    public  Result importData(MultipartFile file){
        categoryService.importData(file);
        return  Result.build(null,ResultCodeEnum.SUCCESS);
    }
}