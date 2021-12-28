package com.sq.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
    private String id;

    private String status;

    private String orderId;

    private String productName;

    private String payNo;

    private String tradeNo;

    @NotNull
    private String payerUid;

    private String payerName;

    @NotNull
    private BigDecimal payerAmount;

    private BigDecimal orderAmount;

    private String payWay;

    private Date paySuccessTime;

    private Date completeTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
