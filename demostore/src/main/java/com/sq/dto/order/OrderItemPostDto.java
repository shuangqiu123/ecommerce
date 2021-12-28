package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemPostDto {
    @NotNull
    private Long itemId;

    @NotNull
    private Integer num;
}
