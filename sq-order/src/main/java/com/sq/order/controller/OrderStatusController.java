package com.sq.order.controller;

import com.sq.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/status")
public class OrderStatusController {

    private final OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderStatusController.class);

    @GetMapping("/setOrderStatus")
    public String setOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") Integer status) {

        logger.info("set orderstatus: ", orderId);

        orderService.setOrderStatus(orderId, status);
        return "success";
    }

}
