package com.sq.management.controller;

import com.sq.dto.ResponseMessage;
import com.sq.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/management/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseMessage getAllOrders(HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }

        responseMessage.setObject(orderService.getAllOrders());
        return responseMessage;
    }

    @GetMapping("/changeOrderStatus")
    public ResponseMessage changeOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") Integer status, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }
        orderService.changeOrderStatus(orderId, status);

        responseMessage.setMessage("success");
        return responseMessage;
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
