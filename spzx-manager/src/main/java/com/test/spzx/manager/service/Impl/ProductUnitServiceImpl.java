package com.test.spzx.manager.service.Impl;

import com.test.spzx.manager.mapper.ProductUnitMapper;
import com.test.spzx.manager.service.ProductUnitService;
import com.test.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper ;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }
}
