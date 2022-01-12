package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDto {
    private String id;
    private Boolean isChanged;
    private List<OrderItemDto> items;
}
