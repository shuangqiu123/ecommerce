package com.sq.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PaymentDto implements Serializable {
    private String id;

    private String status;

    private String orderId;

    private String productName;

    private String payNo;

    private String tradeNo;

    private String payerUid;

    private String payerName;

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
