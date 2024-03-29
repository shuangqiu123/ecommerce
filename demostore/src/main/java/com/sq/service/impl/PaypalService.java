package com.sq.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.payment.PaymentDto;
import com.sq.mapper.PaymentMapper;
import com.sq.config.PaypalConfig;
import com.sq.service.OrderService;
import com.sq.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaypalService implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaypalConfig paypalConfig;

    @Value("${server.frontendURL}")
    private String redirectURL;

    @Override
    @Transactional
    public String createPayment(BigDecimal price, String uid, String orderId) {
        // send payment to paypal
        Amount amount = new Amount("AUD", price.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("create payment");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId(uid);
        payer.setPayerInfo(payerInfo);


        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setIntent("order");

        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(redirectURL + "/payment/" + orderId + "/success");
        redirectUrls.setCancelUrl(redirectURL + "/payment/" + orderId + "/cancel");

        payment.setRedirectUrls(redirectUrls);

        Payment newPayment = null;
        try {
            newPayment = payment.create(paypalConfig.getApiContext());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        PaymentDto paymentDto = new PaymentDto();
        // insert payment info in DTO
        paymentDto.setId(UUID.randomUUID().toString());
        paymentDto.setCreateTime(new Date(System.currentTimeMillis()));
        paymentDto.setUpdateTime(new Date(System.currentTimeMillis()));
        paymentDto.setOrderAmount(price);
        paymentDto.setStatus("0");
        paymentDto.setPayNo(newPayment.getId());
        paymentDto.setPayerUid(newPayment.getPayer().getPayerInfo().getPayerId());
        paymentDto.setPayWay("paypal");
        paymentDto.setPayerAmount(price);
        paymentDto.setPayerUid(uid);
        paymentDto.setOrderId(orderId);
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
    }
}
