package com.test.spzx.manager.mapper;

import com.test.spzx.model.dto.product.ProductDto;
import com.test.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);
    // 保存商品数据
    void save(Product product);

    Product findProductById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}
