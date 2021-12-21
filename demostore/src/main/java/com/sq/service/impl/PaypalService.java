package com.sq.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.PaymentDto;
import com.sq.mapper.PaymentMapper;
import com.sq.config.PaypalConfig;
import com.sq.service.OrderService;
import com.sq.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaypalService implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaypalConfig paypalConfig;
    private final OrderService orderService;

    @Value("${serverURL}")
    private String serverURL;

    @Override
    public String createPayment(PaymentDto paymentDto) {
        // send payment to paypal
        Amount amount = new Amount("AUD", paymentDto.getPayerAmount().toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("create payment");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId(paymentDto.getPayerUid());
        payer.setPayerInfo(payerInfo);


        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setIntent("order");

        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(serverURL + "payment/paypal/success");
        redirectUrls.setCancelUrl(serverURL + "payment/paypal/cancel");

        payment.setRedirectUrls(redirectUrls);

        Payment newPayment = null;
        try {
            newPayment = payment.create(paypalConfig.getApiContext());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        // insert payment info in DTO
        paymentDto.setId(UUID.randomUUID().toString());
        paymentDto.setCreateTime(new Date(System.currentTimeMillis()));
        paymentDto.setUpdateTime(new Date(System.currentTimeMillis()));
        paymentDto.setOrderAmount(paymentDto.getPayerAmount());
        paymentDto.setStatus("0");
        paymentDto.setPayNo(newPayment.getId());
        paymentDto.setPayerUid(newPayment.getPayer().getPayerInfo().getPayerId());
        paymentDto.setPayWay("paypal");
        for(Links links : newPayment.getLinks()){
            if(links.getRel().equals("approval_url")){

                paymentMapper.insertPayment(paymentDto);
                return links.getHref();
            }
        }

        return "";
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(paypalConfig.getApiContext(), paymentExecute);
    }

    @Override
    public void completePayment(String paymentId) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setCompleteTime(new Date(System.currentTimeMillis()));
        paymentDto.setUpdateTime(new Date(System.currentTimeMillis()));
        paymentDto.setPayNo(paymentId);
        paymentDto.setStatus("1");

        paymentMapper.updatePaymentByPayNum(paymentDto);
        com.sq.pojo.Payment paymentByPaymentId = paymentMapper.getPaymentByPaymentId(paymentId);

        orderService.setOrderStatus(paymentByPaymentId.getOrderId(), 1);
    }

    @Override
    public boolean checkPayment(String orderId) {
        List<com.sq.pojo.Payment> payments = paymentMapper.getPaymentByOrderId(orderId);

        for (com.sq.pojo.Payment payment : payments) {
            if (payment.getStatus().equals("1")) {
                return true;
            }
        }
        return false;
    }
}
