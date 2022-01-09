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

    private String description;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String brand;

    private String image;

    private Integer status;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}