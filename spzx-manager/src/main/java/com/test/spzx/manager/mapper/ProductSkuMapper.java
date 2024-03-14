package com.test.spzx.manager.mapper;

import com.test.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku productSku);

    List<ProductSku> findProductSkuById(Long id);

    void updateById(ProductSku productSku);

    void deleteByProductId(Long id);
}
