package com.sq.order.controller;

import com.sq.dto.ResponseMessage;
import com.sq.order.service.OrderService;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import com.sq.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/getOrderListByUserId")
    public ResponseMessage getOrderListByUserId(HttpServletRequest request) {
        List<Order> list = orderService.getAllOrdersByUserId(getUid(request));
        return new ResponseMessage(200, "success", list);
    }

    @GetMapping("/getCartByUserId")
    public ResponseMessage getCartByUserId(HttpServletRequest request) {
        Order order = orderService.getCurrentOrderByUserId(getUid(request));
        return new ResponseMessage(200, "success", order);
    }

    @GetMapping("/createOrderByUserId")
    public ResponseMessage createOrderByUserId(HttpServletRequest request) {
        orderService.createOrderByUserId(getUid(request));
        return new ResponseMessage(200, "success", null);
    }

    @GetMapping("/addItem")
    public ResponseMessage addItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.createOrderByUserId(getUid(request));
        return new ResponseMessage(200, "success", null);
    }

    @GetMapping("/updateItem")
    public ResponseMessage updateItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.createOrderByUserId(getUid(request));
        return new ResponseMessage(200, "success", null);
    }

    @GetMapping("/removeItem")
    public ResponseMessage removeItem(@RequestBody OrderItem orderItem, HttpServletRequest request) {
        orderService.createOrderByUserId(getUid(request));
        return new ResponseMessage(200, "success", null);
    }


    private Long getUid(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String uid = jwtUtil.getUidFromToken(token);
        return Long.parseLong(uid);
    }
}
