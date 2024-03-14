package com.test.spzx.order.mapper;

import com.test.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> findByOrderId(Long id);
}
