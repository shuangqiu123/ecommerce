package com.sq.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sq-order")
public interface OrderFeign {
    @GetMapping("/order/status/setOrderStatus")
    String setOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") Integer status);
}
