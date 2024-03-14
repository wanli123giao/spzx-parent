package com.test.spzx.order.service;

import com.github.pagehelper.PageInfo;
import com.test.spzx.model.dto.h5.OrderInfoDto;
import com.test.spzx.model.entity.order.OrderInfo;
import com.test.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    OrderInfo getByOrderNo(String orderNo);

    void updateOrderStatus(String orderNo);
}
