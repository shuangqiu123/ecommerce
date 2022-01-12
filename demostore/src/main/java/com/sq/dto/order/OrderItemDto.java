package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    private Integer quantity;

    private String title;

    private String description;

    private BigDecimal price;

    private String image;

    private Boolean isNewIn;

    private String brand;

    public OrderItemDto(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
