package com.sq.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.payment.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
    String createPayment(BigDecimal amount, String uid, String orderId) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    void completePayment(String paymentId);
}
