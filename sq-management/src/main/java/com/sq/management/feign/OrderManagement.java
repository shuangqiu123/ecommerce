package com.sq.management.feign;

import com.sq.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "sq-order")
public interface OrderManagement {
    @GetMapping("/order/management/getAllOrders")
    List<Order> getAllOrders();
    @GetMapping("/order/status/setOrderStatus")
    String setOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") Integer status);
}
