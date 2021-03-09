package com.sq.order.controller;

import com.sq.dto.ResponseMessage;
import com.sq.order.service.OrderService;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderListByUserId")
    public ResponseMessage getOrderListByUserId(HttpServletRequest request) {
        List<Order> list = orderService.getAllOrdersByUserId(this.getUid(request));
        return new ResponseMessage(200, "success", list);
    }

    @GetMapping("/getCartByUserId")
    public ResponseMessage getCartByUserId(HttpServletRequest request) {
        Order order = orderService.getCurrentOrderByUserId(this.getUid(request));
        return new ResponseMessage(200, "success", order);
    }


    @PostMapping("/addItem")
    public ResponseMessage addItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.insertCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    @PostMapping("/updateItem")
    public ResponseMessage updateItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.updateCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    @PostMapping("/removeItem")
    public ResponseMessage removeItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.removeCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
