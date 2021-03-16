package com.sq.order.mapper;

import com.sq.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndStatus(Order order);

    List<Order> selectOrderByUserId(Long id);

    void setOrderStatus(String orderId, Integer status);

    List<Order> selectOrder();
}
