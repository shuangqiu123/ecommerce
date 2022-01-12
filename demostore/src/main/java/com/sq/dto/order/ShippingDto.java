package com.sq.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto {
    private String receiverName;

    private String receiverState;

    private String receiverCity;

    private String receiverAddress;

    private String receiverZip;
}
