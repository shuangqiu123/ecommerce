package com.sq.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sq.dto.ResponseMessage;
import com.sq.order.feign.ItemService;
import com.sq.order.mapper.OrderItemMapper;
import com.sq.order.mapper.OrderMapper;
import com.sq.order.service.OrderService;
import com.sq.pojo.Item;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ItemService itemService;

    /**
     * create an order by the user id
     * @param uid user id
     */
    @Override
    public Order createOrderByUserId(Long uid) {
        Order order = new Order();
        order.setUserId(uid);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setUpdateTime(new Date(System.currentTimeMillis()));
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(0);
        orderMapper.insert(order);

        return order;
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

        // if the user has not created an order before
        if (currentOrder == null) {
            return createOrderByUserId(uid);
        }

        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(currentOrder.getOrderId());
        currentOrder.setOrderItemList(orderItems);
        return currentOrder;
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

        ResponseMessage responseMessage = itemService.getItemById(orderItem.getItemId());
        LinkedHashMap linkedHashMap = (LinkedHashMap) responseMessage.getObject();

        // feign converts the object into a linked hashmap
        Item item = JSON.parseObject(JSON.toJSONString(linkedHashMap), Item.class);

        // set the price and total fee for the item
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setPrice(item.getPrice());
        orderItem.setTotalFee(item.getPrice().multiply(new BigDecimal(orderItem.getNum())));
        orderItem.setPicPath(item.getImage());
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
