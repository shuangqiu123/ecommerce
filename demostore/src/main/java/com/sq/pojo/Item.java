package com.sq.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * item
 * @author 
 */
@Data
public class Item implements Serializable {

    private Long id;


    private String title;


    private String sellPoint;


    private BigDecimal price;


    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}