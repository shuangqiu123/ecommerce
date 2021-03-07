package com.sq.order.service.impl;

import com.sq.order.mapper.OrderItemMapper;
import com.sq.order.mapper.OrderMapper;
import com.sq.order.service.OrderService;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;
    /**
     * create an order by the user id
     * @param uid user id
     */
    @Override
    public void createOrderByUserId(Long uid) {
        Order order = new Order();
        order.setUserId(uid);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(0);
        orderMapper.insert(order);
    }

    /**
     * get the current order with status = 0, i.e. the cart
     * @param uid
     * @return
     */
    @Override
    public Order getCurrentOrderByUserId(Long uid) {
        Order order = new Order();
        order.setUserId(uid);
        order.setStatus(0);

        Order currentOrder = orderMapper.selectByUserIdAndStatus(order);
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(currentOrder.getOrderId());
        currentOrder.setOrderItemList(orderItems);

        return orderMapper.selectByUserIdAndStatus(currentOrder);
    }

    /**
     * get all listed orders by user id
     * @param uid
     * @return
     */
    @Override
    public List<Order> getAllOrdersByUserId(Long uid) {
        List<Order> orders = orderMapper.selectOrderByUserId(uid);

        for (Order o : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(o.getOrderId());
            o.setOrderItemList(orderItems);
        }
        return orders;
    }

    @Override
    public void insertCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());
        orderItem.setCreateTime(new Date(System.currentTimeMillis()));
        orderItem.setUpdateTime(new Date(System.currentTimeMillis()));
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void removeCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());
        orderItemMapper.deleteByOrderIdAndItemId(orderItem);
    }

    @Override
    public void updateCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());
        orderItemMapper.updateByOrderIdAndItemId(orderItem);
    }
}
