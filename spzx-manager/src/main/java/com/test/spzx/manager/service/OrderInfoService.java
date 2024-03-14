package com.test.spzx.manager.service;

import com.test.spzx.model.dto.order.OrderStatisticsDto;
import com.test.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
