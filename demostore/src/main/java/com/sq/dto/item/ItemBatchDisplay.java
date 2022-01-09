package com.sq.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemBatchDisplay {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer num;
    private Boolean isNewIn;
    private String image;
    private String brand;
}
