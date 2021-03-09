package com.sq.order.service;

import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrderByUserId(Long uid);

    Order getCurrentOrderByUserId(Long uid);

    List<Order> getAllOrdersByUserId(Long uid);

    void insertCartItem(Long uid, OrderItem orderItem);
    void removeCartItem(Long uid, OrderItem orderItem);
    void updateCartItem(Long uid, OrderItem orderItem);


}
