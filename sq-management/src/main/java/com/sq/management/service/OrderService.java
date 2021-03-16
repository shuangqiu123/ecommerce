package com.sq.management.service;

import com.sq.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    void changeOrderStatus(String orderId, Integer status);
}
