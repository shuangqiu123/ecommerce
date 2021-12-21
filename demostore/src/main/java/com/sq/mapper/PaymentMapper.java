package com.sq.mapper;

import com.sq.dto.PaymentDto;
import com.sq.pojo.Payment;

import java.util.List;

public interface PaymentMapper {

    void insertPayment(PaymentDto paymentDto);

    void updatePaymentByPayNum(PaymentDto paymentDto);

    List<Payment> getPaymentByOrderId(String orderId);

    Payment getPaymentByPaymentId(String paymentId);
}
