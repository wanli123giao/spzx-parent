package com.test.spzx.product.service;

import com.test.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}
