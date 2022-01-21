package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetDto {
    private List<OrderItemDto> items;
    private Double price;
    private Boolean viewOnly;
    private ShippingDto shippingAddress;
}
