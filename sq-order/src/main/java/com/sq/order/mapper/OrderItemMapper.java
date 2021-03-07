package com.sq.order.mapper;

import com.sq.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectByOrderId(String orderId);

    void updateByOrderIdAndItemId(OrderItem orderItem);

    void deleteByOrderIdAndItemId(OrderItem orderItem);

    void insertByOrderIdAndItemId(OrderItem orderItem);
}
