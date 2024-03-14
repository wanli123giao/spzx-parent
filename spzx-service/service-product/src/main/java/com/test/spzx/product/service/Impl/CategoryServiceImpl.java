package com.test.spzx.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.test.spzx.model.entity.product.Category;
import com.test.spzx.product.mapper.CategoryMapper;
import com.test.spzx.product.service.CategoryService;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    //category:one
    @Override
    public List<Category> selectOneCategory() {
        //1 查询redis，是否有所有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");
        //2 如果redis包含所有一级分类，直接返回
        if(StringUtils.hasText(categoryOneJson)){
            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existCategoryList;
        }
        //3 如果redis没有所有一级分类，查询数据库，把数据库查询内容返回，并且查询内容放到redis里面
        List<Category> categoryList = categoryMapper.selectOneCategory();
        redisTemplate.opsForValue().set("category:one",
                JSON.toJSONString(categoryList), //把list转换成json存入redis
                7, TimeUnit.DAYS);
        //查询内容放到redis里面
        return categoryList;
    }

    @Cacheable(value = "category",key = "'all'")//中间加上单引号变成字符串
    @Override
    public List<Category> findCategoryTree() {
        //1 查询所有分类 返回list集合
        List<Category> allCategoryList = categoryMapper.findAll();
        //2 遍历所有分类list集合，通过条件 parentid=0得到所有一级分类
        List<Category> OneCategoryList =
                allCategoryList.stream()
                        .filter(item -> item.getParentId().longValue() == 0)
                        .collect(Collectors.toList());
        //3 遍历所有一级分类list集合，条件判断： id = parentid，得到一级下面二级分类
        OneCategoryList.forEach(oneCategory ->{
            List<Category> twoCategoryList =
                    allCategoryList.stream()
                            .filter(item -> item.getParentId() == oneCategory.getId())
                            .collect(Collectors.toList());
            //把二级分类封装到一级分类里面
            oneCategory.setChildren(twoCategoryList);
            //4 遍历所有二级分类， 条件判断： id = parentid，得到二级下面三级分类
            twoCategoryList.forEach(twoCategory ->{
                List<Category> threeCategoryList =
                        allCategoryList.stream()
                                .filter(item -> item.getParentId() == twoCategory.getId())
                                .collect(Collectors.toList());
                twoCategory.setChildren(threeCategoryList);
            });
        });

        return OneCategoryList;
    }
}
