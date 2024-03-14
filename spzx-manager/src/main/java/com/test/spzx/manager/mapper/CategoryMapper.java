package com.test.spzx.manager.mapper;

import com.test.spzx.model.entity.product.Category;
import com.test.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //1 根据id条件值进行查询，返回list集合
    List<Category> selectCategoryByParentId(Long id);
    //2 遍历返回list集合，
    int selectCountByParentId(Long id);
//导出下载

    List<Category> findAll();

    void batchInsert(List<CategoryExcelVo> categoryList);
}
