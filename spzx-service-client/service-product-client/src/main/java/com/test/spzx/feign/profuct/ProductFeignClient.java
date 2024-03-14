package com.test.spzx.feign.profuct;

import com.test.spzx.model.dto.product.SkuSaleDto;
import com.test.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product")

public interface ProductFeignClient {
    @GetMapping("/api/product/getBySkuId/{skuId}")
    public  ProductSku getBySkuId(@PathVariable Long skuId) ;
    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);
}