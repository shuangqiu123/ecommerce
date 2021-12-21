package com.sq.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * order_shipping
 * @author 
 */
@Data
public class OrderShipping implements Serializable {

    private String orderId;


    private String receiverName;


    private String receiverPhone;


    private String receiverMobile;


    private String receiverState;


    private String receiverCity;


    private String receiverDistrict;


    private String receiverAddress;

    private String receiverZip;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}