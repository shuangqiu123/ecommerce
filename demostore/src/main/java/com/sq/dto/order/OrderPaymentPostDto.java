package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentPostDto {
    @NotNull
    private String orderId;
    @NotNull
    private String paymentId;
    @NotNull
    private String payerId;
}
