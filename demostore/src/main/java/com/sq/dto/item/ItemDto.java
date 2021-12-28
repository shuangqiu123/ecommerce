package com.sq.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String sellPoint;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer num;

    @NotNull
    private Integer limitNum;

    @NotNull
    private String image;

    @NotNull
    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;
}
