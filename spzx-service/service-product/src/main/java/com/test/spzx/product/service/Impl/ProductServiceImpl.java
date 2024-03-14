package com.test.spzx.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.spzx.model.dto.h5.ProductSkuDto;
import com.test.spzx.model.dto.product.SkuSaleDto;
import com.test.spzx.model.entity.product.Product;
import com.test.spzx.model.entity.product.ProductDetails;
import com.test.spzx.model.entity.product.ProductSku;
import com.test.spzx.model.vo.h5.ProductItemVo;
import com.test.spzx.product.mapper.ProductDetailsMapper;
import com.test.spzx.product.mapper.ProductMapper;
import com.test.spzx.product.mapper.ProductSkuMapper;
import com.test.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public List<ProductSku> selectProductSkuBySale() {
        return productSkuMapper.selectProductSkuBySale();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkus = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkus);

    }

    @Override
    public ProductItemVo item(Long skuId) {
        //1 创建vo对象，用于封装最终数据
        ProductItemVo productItemVo = new ProductItemVo();
        //2 根据skuId获取sku信息
       ProductSku productSku= productSkuMapper.getById(skuId);
        //3 根据第二步获取sku，从sku获取productId，获取商品信息\
        Long productId = productSku.getProductId();
       Product product= productMapper.getById(productId);
        //4 productId，获取商品详情信息
       ProductDetails productDetails=productDetailsMapper.getByProductId(productId);
        //5 封装map集合 == 商品规格对应商品skuId信息\
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        //根据商品id获取商品所有sku列表
        List<ProductSku> productSkus = productSkuMapper.findByProductId(productId);
        productSkus.forEach(item ->{
            skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });
        //6 把需要数据封装到productItemVo里面
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        //封装详情图片 list集合
//        String imageUrls = productDetails.getImageUrls();
//        String[] split = imageUrls.split(",");
//        List<String> list = Arrays.asList(split);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));

        //封装轮播图 list集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));

//        @Schema(description = "商品规格信息")
//        private JSONArray specValueList;
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVo;

        //远程调用：根据skuId返回sku信息

    }

    @Override
    public ProductSku getBySkuId(Long skuId) {

        return productSkuMapper.getById(skuId);
    }

    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                productSkuMapper.updateSale(skuSaleDto.getSkuId(), skuSaleDto.getNum());
            }
        }
        return true;
    }

}
