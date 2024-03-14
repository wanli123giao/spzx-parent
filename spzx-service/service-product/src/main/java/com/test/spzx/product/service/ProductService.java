package com.test.spzx.product.service;

import com.github.pagehelper.PageInfo;
import com.test.spzx.model.dto.h5.ProductSkuDto;
import com.test.spzx.model.dto.product.SkuSaleDto;
import com.test.spzx.model.entity.product.ProductSku;
import com.test.spzx.model.vo.h5.ProductItemVo;

import java.util.List;

public interface ProductService {
    List<ProductSku> selectProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}
