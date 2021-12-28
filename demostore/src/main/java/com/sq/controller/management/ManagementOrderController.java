package com.sq.controller.management;

import com.sq.dto.ResponseMessage;
import com.sq.dto.order.OrderDto;
import com.sq.pojo.Order;
import com.sq.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/order")
public class ManagementOrderController {
    private final OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseMessage getAllOrders(HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }
        List<Order> allOrders = orderService.getAllOrders();
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
        });
        responseMessage.setObject(orderDtoList);
        return responseMessage;
    }

    @GetMapping("/changeOrderStatus")
    public ResponseMessage changeOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") Integer status, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }
        orderService.setOrderStatus(orderId, status);

        responseMessage.setMessage("success");
        return responseMessage;
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
