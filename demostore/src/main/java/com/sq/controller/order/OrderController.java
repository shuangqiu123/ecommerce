package com.sq.controller.order;

import com.sq.dto.ResponseMessage;
import com.sq.dto.order.*;
import com.sq.service.OrderService;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import com.sq.pojo.OrderShipping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getOrderListByUserId")
    public ResponseMessage getOrderListByUserId(HttpServletRequest request) {
        List<Order> list = orderService.getAllOrdersByUserId(this.getUid(request));
        return new ResponseMessage(200, "success", list);
    }

    @PostMapping("/createOrder")
    public ResponseMessage createOrder(@RequestBody @Valid OrderPostDto orderPostDto, HttpServletRequest request) {
        OrderCreationDto orderCreationDto = orderService.createOrder(getUid(request), orderPostDto);
        return new ResponseMessage(200, "success", orderCreationDto);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<ResponseMessage> getOrder(@RequestParam String orderId, HttpServletRequest request) {
        ResponseMessage responseMessage = orderService.getOrder(getUid(request), orderId);
        if (responseMessage.getCode() == 403) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseMessage);
        }
        else if (responseMessage.getCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok().body(responseMessage);
    }

    @PostMapping("/getOrderPayment")
    public ResponseEntity<ResponseMessage> getOrderPayment(@RequestBody @Valid OrderCompletionPostDto orderCompletionPostDto, HttpServletRequest request) {
        ResponseMessage responseMessage = orderService.getOrderPayment(getUid(request), orderCompletionPostDto);
        if (responseMessage.getCode() == 403) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseMessage);
        }
        else if (responseMessage.getCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok().body(responseMessage);
    }

    @PostMapping("/payOrder")
    public ResponseEntity<ResponseMessage> payOrder(@RequestBody @Valid OrderPaymentPostDto orderPaymentPostDto, HttpServletRequest request) {
        ResponseMessage responseMessage = orderService.executeOrderPayment(getUid(request), orderPaymentPostDto);
        if (responseMessage.getCode() == 403) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseMessage);
        }
        else if (responseMessage.getCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok().body(responseMessage);
    }

    @PostMapping("/addItem")
    public ResponseMessage addItem(@RequestBody @Valid OrderItemPostDto orderItemPostDto, HttpServletRequest request) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemPostDto, orderItem);
        orderService.insertCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    @PostMapping("/updateItem")
    public ResponseMessage updateItem(@RequestBody @Valid OrderItemPostDto orderItemPostDto, HttpServletRequest request) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemPostDto, orderItem);
        orderService.updateCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    @PostMapping("/removeItem")
    public ResponseMessage removeItem(@RequestBody @Valid OrderItemPostDto orderItemPostDto, HttpServletRequest request) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemPostDto, orderItem);
        orderService.removeCartItem(this.getUid(request), orderItem);
        return new ResponseMessage(200, "success", null);
    }

    @GetMapping("/addAddress")
    public ResponseMessage addAddress(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("orderId") String orderId, HttpServletRequest request) {
        OrderShipping orderShipping = new OrderShipping();
        orderShipping.setReceiverAddress(address);
        orderShipping.setReceiverName(name);
        orderShipping.setOrderId(orderId);

        orderService.createShipping(orderShipping);
        return new ResponseMessage(200, "success", null);
    }

    @GetMapping("/cancelOrder")
    public ResponseMessage cancelOrder(@RequestParam("orderId") String orderId, HttpServletRequest request) {
        return orderService.cancelOrder(orderId, getUid(request));
    }


    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
