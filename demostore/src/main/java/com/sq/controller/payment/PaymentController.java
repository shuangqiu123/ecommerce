package com.sq.controller.payment;


import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sq.dto.PaymentDto;
import com.sq.dto.ResponseMessage;
import com.sq.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/paypal/init")
    public ResponseMessage initPaypalPayment(@RequestBody PaymentDto paymentDto, HttpServletRequest request) throws Exception{
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(200);

        try {
            String url = paymentService.createPayment(paymentDto);
            responseMessage.setObject(url);
            responseMessage.setMessage("success");

        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
            responseMessage.setMessage("fail");
        }

        return responseMessage;
    }

    @GetMapping(value = "/paypal/success", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                paymentService.completePayment(paymentId);
            }
        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
        }

        return "<!DOCTYPE html><html><script>window.close();</script></html>";
    }


    @GetMapping("/checkPayment")
    public ResponseMessage checkPayment(@RequestParam("orderId") String orderId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(200);

        if (paymentService.checkPayment(orderId)) {
            responseMessage.setMessage("success");
        }
        else {
            responseMessage.setMessage("fail");
        }

        return responseMessage;
    }
}
