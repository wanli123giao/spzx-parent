package com.test.spzx.cart.service;

import com.test.spzx.model.dto.h5.CartInfo;

import java.util.List;

public interface CartService {
    void clearCart();

    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    List<CartInfo> getAllCkecked();

    void deleteChecked();
}
