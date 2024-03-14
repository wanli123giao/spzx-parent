package com.test.spzx.manager.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.test.spzx.common.exception.GuiguException;
import com.test.spzx.manager.listener.ExcelListener;
import com.test.spzx.manager.mapper.CategoryMapper;
import com.test.spzx.manager.service.CategoryService;
import com.test.spzx.model.entity.product.Category;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import com.test.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findByParentId(Long id) {
        //1 根据id条件值进行查询，返回list集合
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);
        // SELECT * FROM category WHERE parent_id=id
        //2 遍历返回list集合，
        // 判断每个分类是否有下一层分类，如果有设置 hasChildren = true
        if (!CollectionUtils.isEmpty(categoryList)){
            categoryList.forEach(category -> {
                // 判断每个分类是否有下一层分类
                //SELECT count(*) FROM category WHERE parent_id=1
               int count= categoryMapper.selectCountByParentId(category.getId());
                if (count>0){
                    category.setHasChildren(true);
                }else{
                    category.setHasChildren(false);
                }
            });
        }

        return categoryList;
    }
//导出下载
    @Override
    public void exportData(HttpServletResponse response) {
        try {//1 设置响应头信息和其他信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            //设置响应头信息 Content-disposition,
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //2 调用mapper方法查询所有分类，返回list集合
           List<Category> CategoryList= categoryMapper.findAll();
            //最终数据list集合
            List<CategoryExcelVo>
                    listExcelVo = new ArrayList<>();
            //把category值获取出来，设置到categoryExcelVo里面
            for (Category category:CategoryList) {
                CategoryExcelVo
                        categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                listExcelVo.add(categoryExcelVo);
            }
            //3 调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(listExcelVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }


    }
//导入
    @Override
    public void importData(MultipartFile file) {
        //
        //创建监听器对象，传递mapper对象
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,excelListener).sheet().doRead();
        } catch (Exception e) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
