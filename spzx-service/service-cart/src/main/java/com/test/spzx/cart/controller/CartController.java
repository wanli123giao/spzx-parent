package com.test.spzx.cart.controller;

import com.test.spzx.cart.service.CartService;
import com.test.spzx.model.dto.h5.CartInfo;
import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(summary="清空购物车")
    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "查询购物车")
    @GetMapping("auth/cartList")
    public Result cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除购物车商品")
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //skuId:商品sku的id值
    //skuNum：商品数量
    @Operation(summary = "添加购物车")
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId,
                            @PathVariable Integer skuNum) {
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary="更新购物车商品选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable(value = "skuId") Long skuId,
                            @Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.checkCart(skuId,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary="更新购物车商品全部选中状态")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary="选中的购物车")
    @GetMapping(value = "/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked() {
        List<CartInfo> cartInfoList = cartService.getAllCkecked() ;
        return cartInfoList;
    }
    @Operation(summary="删除选中的购物车")
    @GetMapping(value = "/auth/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked() ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}
