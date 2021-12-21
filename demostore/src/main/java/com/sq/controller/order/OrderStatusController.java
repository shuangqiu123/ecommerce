package com.sq.controller.order;

import com.sq.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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
