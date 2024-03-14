package com.test.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.test.spzx.model.entity.product.Brand;

import java.util.List;

public interface   BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);
//    查询所有品牌
    List<Brand> findAll();
}
