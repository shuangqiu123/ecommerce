package com.sq.service;

import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import com.sq.pojo.OrderShipping;

import java.util.List;

public interface OrderService {

    Order createOrderByUserId(Long uid);

    Order getCurrentOrderByUserId(Long uid);

    List<Order> getAllOrdersByUserId(Long uid);

    List<Order> getAllOrders();

    void insertCartItem(Long uid, OrderItem orderItem);

    void removeCartItem(Long uid, OrderItem orderItem);

    void updateCartItem(Long uid, OrderItem orderItem);

    void createShipping(OrderShipping orderShipping);

    void updateShipping(OrderShipping orderShipping);

    void setOrderStatus(String orderId, Integer status);
}
