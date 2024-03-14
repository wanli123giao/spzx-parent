package com.test.spzx.manager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.spzx.manager.mapper.CategoryBrandMapper;
import com.test.spzx.manager.service.CategoryBrandService;
import com.test.spzx.model.dto.product.CategoryBrandDto;
import com.test.spzx.model.entity.product.Brand;
import com.test.spzx.model.entity.product.CategoryBrand;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> categoryBrandList=categoryBrandMapper.findByPage(categoryBrandDto);
       PageInfo<CategoryBrand> pageInfo=new PageInfo<>(categoryBrandList);
        return pageInfo;

    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand);

    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {

        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
