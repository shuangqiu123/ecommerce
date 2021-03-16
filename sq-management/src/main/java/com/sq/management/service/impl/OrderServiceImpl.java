package com.sq.management.service.impl;

import com.sq.dto.OrderDto;
import com.sq.management.feign.OrderManagement;
import com.sq.management.mapper.UserMapper;
import com.sq.management.service.OrderService;
import com.sq.pojo.Member;
import com.sq.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderManagement.getAllOrders();
        List<OrderDto> orderDtos = new ArrayList<>();
        HashMap<Long, Member> cache = new HashMap<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            if (cache.containsKey(order.getUserId())) {
                orderDto.setMember(cache.get(order.getUserId()));
            }
            else {
                Member member = userMapper.getUserByUserId(order.getUserId());
                cache.put(order.getUserId(), member);
                orderDto.setMember(cache.get(order.getUserId()));
            }
            orderDto.setOrder(order);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    @Override
    public void changeOrderStatus(String orderId, Integer status) {
        orderManagement.setOrderStatus(orderId, status);
    }

}
