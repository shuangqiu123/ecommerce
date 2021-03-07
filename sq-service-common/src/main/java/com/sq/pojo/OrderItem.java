package com.sq.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItem {
    private String id;

    //  item id
    private Long itemId;

    // order id
    private String orderId;


    private Integer num;


    private String title;


    private BigDecimal price;


    private BigDecimal totalFee;


    private String picPath;



    private Integer status;


    private Date createTime;


    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
