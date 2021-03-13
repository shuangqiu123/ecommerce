package com.sq.payment.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.PaymentDto;

public interface PaymentService {
    String createPayment(PaymentDto payment) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    void completePayment(String paymentId);

    boolean checkPayment(String orderId);
}
