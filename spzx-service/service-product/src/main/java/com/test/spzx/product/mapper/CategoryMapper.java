package com.test.spzx.product.mapper;

import com.test.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectOneCategory();

    List<Category> findAll();
}
