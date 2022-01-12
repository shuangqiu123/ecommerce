package com.sq.service.impl;

import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.ResponseMessage;
import com.sq.dto.order.*;
import com.sq.dto.payment.PaymentDto;
import com.sq.service.ItemService;
import com.sq.mapper.OrderItemMapper;
import com.sq.mapper.OrderMapper;
import com.sq.mapper.OrderShippingMapper;
import com.sq.service.OrderService;
import com.sq.pojo.Item;
import com.sq.pojo.Order;
import com.sq.pojo.OrderItem;
import com.sq.pojo.OrderShipping;
import com.sq.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final PaymentService paymentService;

    private final OrderShippingMapper orderShippingMapper;

    private final ItemService itemService;

    /**
     * create an order by the user id
     * @param uid user id
     */
    @Transactional
    @Override
    public Order createOrderByUserId(Long uid) {
        Order order = new Order();
        order.setUserId(uid);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setUpdateTime(new Date(System.currentTimeMillis()));
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(0);
        orderMapper.insert(order);
        return order;
    }

    /**
     * get the current order with status = 0, i.e. the cart
     * @param uid
     * @return
     */
    @Override
    public Order getCurrentOrderByUserId(Long uid) {
        Order order = new Order();
        order.setUserId(uid);
        order.setStatus(0);

        Order currentOrder = orderMapper.selectByUserIdAndStatus(order);

        // if the user has not created an order before
        if (currentOrder == null) {
            return createOrderByUserId(uid);
        }

        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(currentOrder.getOrderId());
        currentOrder.setOrderItemList(orderItems);
        return currentOrder;
    }

    /**
     * get all listed orders by user id
     * @param uid
     * @return
     */
    @Override
    public List<Order> getAllOrdersByUserId(Long uid) {
        List<Order> orders = orderMapper.selectOrderByUserId(uid);

        for (Order o : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(o.getOrderId());
            o.setOrderItemList(orderItems);
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderMapper.selectOrder();

        for (Order o : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(o.getOrderId());
            o.setOrderItemList(orderItems);
        }
        return orders;
    }

    @Transactional
    @Override
    public void insertCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        Long itemId = orderItem.getItemId();

        // do nothing if it is already in the cart
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        for (OrderItem oi : orderItems) {
            if (oi.getItemId().equals(itemId)) {
                return;
            }
        }
        Item item = itemService.getItemById(itemId);

        // set the price and total fee for the item
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setTotalFee(item.getPrice().multiply(new BigDecimal(orderItem.getNum())));
        orderItem.setPicPath(item.getImage());
        orderItem.setOrderId(order.getOrderId());
        orderItem.setCreateTime(new Date(System.currentTimeMillis()));
        orderItem.setUpdateTime(new Date(System.currentTimeMillis()));
        orderItemMapper.insert(orderItem);
    }

    @Transactional
    @Override
    public void removeCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());
        orderItemMapper.deleteByOrderIdAndItemId(orderItem);
    }

    @Transactional
    @Override
    public void updateCartItem(Long uid, OrderItem orderItem) {
        Order order = getCurrentOrderByUserId(uid);
        orderItem.setOrderId(order.getOrderId());

        //reset the total price
        int num = orderItem.getNum();
        BigDecimal price = orderItem.getPrice();
        orderItem.setTotalFee(price.multiply(new BigDecimal(num)));
        orderItem.setUpdateTime(new Date(System.currentTimeMillis()));

        orderItemMapper.updateByOrderIdAndItemId(orderItem);
    }

    @Override
    @Transactional
    public OrderCreationDto createOrder(Long uid, OrderPostDto orderPostDto) {
        List<OrderItemDto> orderItems = orderPostDto.getItems();
        List<OrderItemDto> returnedOrderItems = new ArrayList<>();
        OrderCreationDto orderCreationDto = new OrderCreationDto();
        boolean isChanged = false;
        for (OrderItemDto orderItem: orderItems) {
            OrderItemDto returnedOrderItem = itemService.checkItemAvailability(orderItem.getId(), orderItem.getQuantity());
            if (returnedOrderItem == null || !returnedOrderItem.getQuantity().equals(orderItem.getQuantity())) {
                isChanged = true;
            }
            if (returnedOrderItem != null) {
                returnedOrderItems.add(returnedOrderItem);
            }
        }
        if (isChanged) {
            orderCreationDto.setIsChanged(true);
            orderCreationDto.setItems(returnedOrderItems);
            return orderCreationDto;
        }
        Order order = createOrderByUserId(uid);

        orderItems.stream().forEach(orderItemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setItemId(orderItemDto.getId());
            orderItem.setNum(orderItemDto.getQuantity());
            orderItem.setUpdateTime(new Date());
            orderItem.setCreateTime(new Date());
            orderItemMapper.insert(orderItem);
        });
        orderCreationDto.setId(order.getOrderId());
        return orderCreationDto;
    }

    @Transactional
    @Override
    public void createShipping(OrderShipping orderShipping) {

        orderShipping.setCreated(new Date(System.currentTimeMillis()));
        orderShipping.setUpdated(new Date(System.currentTimeMillis()));

        orderShippingMapper.insert(orderShipping);
    }

    @Override
    public void updateShipping(OrderShipping orderShipping) {
        orderShipping.setUpdated(new Date(System.currentTimeMillis()));
        orderShippingMapper.updateByOrderId(orderShipping);
    }

    @Override
    @Transactional
    public void setOrderStatus(String orderId, Integer status) {
        orderMapper.setOrderStatus(orderId, status);
    }

    @Override
    public ResponseMessage getOrder(Long uid, String orderId) {
        ResponseMessage response = checkUserAccess(uid, orderId);
        if (response != null) {
            return response;
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
        OrderGetDto orderGetDto = new OrderGetDto();
        List<OrderItemDto> orderItemDtos = orderItems.stream().map((orderItem -> {
            Item item = orderItem.getItem();
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setQuantity(orderItem.getNum());
            BeanUtils.copyProperties(item, orderItemDto);
            return orderItemDto;
        })).collect(Collectors.toList());
        orderGetDto.setItems(orderItemDtos);
        orderGetDto.setPrice(calculatePrice(orderItems));
        return new ResponseMessage(200, "success", orderGetDto);
    }

    @Override
    @Transactional
    public ResponseMessage getOrderPayment(Long uid, OrderCompletionPostDto orderCompletionPostDto) {
        String orderId = orderCompletionPostDto.getOrderId();
        ResponseMessage response = checkUserAccess(uid, orderId);
        if (response != null) {
            return response;
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order.getStatus() == 1) {
            return new ResponseMessage(404, "completed order", null);
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);

        // check if order is not sync
        ResponseMessage responseMessage = syncOrder(orderItems);
        if (responseMessage != null) {
            return responseMessage;
        }

        // save the shipping information
        ShippingDto shippingDto = orderCompletionPostDto.getShippingDto();
        OrderShipping orderShipping = new OrderShipping();
        BeanUtils.copyProperties(shippingDto, orderShipping);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShipping.setOrderId(orderId);
        orderShippingMapper.insert(orderShipping);

        // continue to payment
        double price = calculatePrice(orderItems);
        String url = "";
        try {
            url = paymentService.createPayment(new BigDecimal(price), uid.toString(), orderId);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseMessage(404, "paypal error", null);
        }
        OrderCompletionDto orderCompletionDto = new OrderCompletionDto();
        orderCompletionDto.setUrl(url);
        return new ResponseMessage(200, "success", orderCompletionDto);
    }

    @Override
    @Transactional
    public ResponseMessage executeOrderPayment(Long uid, OrderPaymentPostDto orderPaymentPostDto) {
        String orderId = orderPaymentPostDto.getOrderId();
        ResponseMessage response = checkUserAccess(uid, orderId);
        if (response != null) {
            return response;
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order.getStatus() == 1) {
            return new ResponseMessage(404, "completed order", null);
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);

        // check if order is not sync
        response = syncOrder(orderItems);
        if (response != null) {
            return response;
        }

        try {
            paymentService.executePayment(orderPaymentPostDto.getPaymentId(), orderPaymentPostDto.getPayerId());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseMessage(404, "payment execution error", null);
        }
        setOrderStatus(orderId, 1);
        // To-Do deduct items

        paymentService.completePayment(orderPaymentPostDto.getPaymentId());
        return new ResponseMessage(200, "success", null);
    }

    private ResponseMessage checkUserAccess(Long uid, String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return new ResponseMessage(404, "order not found", null);
        }
        if (!order.getUserId().equals(uid)) {
            return new ResponseMessage(403, "Forbidden Access", null);
        }
        return null;
    }

    @Transactional
    public ResponseMessage syncOrder(List<OrderItem> orderItems) {
        boolean isChanged = false;
        for (OrderItem orderItem: orderItems) {
            OrderItemDto orderItemDto = itemService.checkItemAvailability(orderItem.getItemId(), orderItem.getNum());
            if (orderItemDto == null) {
                isChanged = true;
                orderItemMapper.deleteByPrimaryKey(orderItem.getId());
            }
            else if (!orderItemDto.getQuantity().equals(orderItem.getNum())) {
                isChanged = true;
                orderItem.setNum(orderItemDto.getQuantity());
                orderItemMapper.updateByPrimaryKeySelective(orderItem);
            }
        }
        if (isChanged) {
            OrderCompletionDto orderCompletionDto = new OrderCompletionDto();
            orderCompletionDto.setIsChanged(true);
            return new ResponseMessage(200, "success", orderCompletionDto);
        }
        return null;
    }

    private double calculatePrice(List<OrderItem> orderItems) {
        double price = 0;
        for (OrderItem orderItem : orderItems) {
            price += orderItem.getItem().getPrice().doubleValue() * orderItem.getNum();
        }
        return price;
    }
}
