package com.sq.dto;

import com.sq.pojo.Member;
import com.sq.pojo.Order;
import com.sq.pojo.Payment;
import lombok.Data;

@Data
public class OrderDto {
    private Order order;
    private Member member;
    private Payment payment;
}
