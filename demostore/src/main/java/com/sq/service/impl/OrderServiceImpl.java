package com.sq.service.impl;

import com.sq.service.ItemService;
import com.sq.mapper.OrderItemMapper;
import com.sq.mapper.OrderMapper;
import com.sq.mapper.OrderShippingMapper;
import com.sq.service.OrderService;
import com.sq.pojo.Item;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import com.sq.pojo.OrderShipping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final OrderShippingMapper orderShippingMapper;

    private final ItemService itemService;

    /**
     * create an order by the user id
     * @param uid user id
     */
    @Transactional
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
    public List<Order> getAllOrders() {
        List<Order> orders = orderMapper.selectOrder();

        for (Order o : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(o.getOrderId());
            o.setOrderItemList(orderItems);
        }
        return orders;
    }

    @Transactional
    @Override
    public void insertCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        Long itemId = orderItem.getItemId();

        // do nothing if it is already in the cart
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        for (OrderItem oi : orderItems) {
            if (oi.getItemId().equals(itemId)) {
                return;
            }
        }
        Item item = itemService.getItemById(itemId);

        // set the price and total fee for the item
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setTotalFee(item.getPrice().multiply(new BigDecimal(orderItem.getNum())));
        orderItem.setPicPath(item.getImage());
        orderItem.setOrderId(order.getOrderId());
        orderItem.setCreateTime(new Date(System.currentTimeMillis()));
        orderItem.setUpdateTime(new Date(System.currentTimeMillis()));
        orderItemMapper.insert(orderItem);
    }

    @Transactional
    @Override
    public void removeCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());
        orderItemMapper.deleteByOrderIdAndItemId(orderItem);
    }

    @Transactional
    @Override
    public void updateCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());

        //reset the total price
        int num = orderItem.getNum();
        BigDecimal price = orderItem.getPrice();
        orderItem.setTotalFee(price.multiply(new BigDecimal(num)));
        orderItem.setUpdateTime(new Date(System.currentTimeMillis()));

        orderItemMapper.updateByOrderIdAndItemId(orderItem);
    }

    @Transactional
    @Override
    public void createShipping(OrderShipping orderShipping) {

        orderShipping.setCreated(new Date(System.currentTimeMillis()));
        orderShipping.setUpdated(new Date(System.currentTimeMillis()));

        orderShippingMapper.insert(orderShipping);
    }

    @Override
    public void updateShipping(OrderShipping orderShipping) {
        orderShipping.setUpdated(new Date(System.currentTimeMillis()));
        orderShippingMapper.updateByOrderId(orderShipping);
    }

    @Override
    public void setOrderStatus(String orderId, Integer status) {
        orderMapper.setOrderStatus(orderId, status);
    }
}
