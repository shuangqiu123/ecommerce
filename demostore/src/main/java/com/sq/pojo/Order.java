package com.sq.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * order
 * @author
 */
@Data
@NoArgsConstructor
public class Order implements Serializable {
    private String orderId;

    private BigDecimal payment;

    private Integer paymentType;

    private BigDecimal postFee;

    // 0 for new, 1 for unpaid, 2 for paid
    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private Long userId;

    private String buyerMessage;

    private String buyerNick;

    private Boolean buyerComment;

    private String uniqueKey;

    private List<OrderItem> orderItemList;

    private static final long serialVersionUID = 1L;
}