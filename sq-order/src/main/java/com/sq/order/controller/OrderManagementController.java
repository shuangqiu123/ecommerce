package com.sq.order.controller;

import com.sq.order.service.OrderService;
import com.sq.pojo.Order;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/management")
public class OrderManagementController {

    private final OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderStatusController.class);

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        logger.info("get all order");
        List<Order> allOrders = orderService.getAllOrders();
        return allOrders;
    }
}
