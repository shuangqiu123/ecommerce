package com.sq.dto.item;

import com.sq.constant.Category;
import com.sq.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemBatchPostDto {

    @NotNull
    @Min(1)
    private Integer pageNum;

    @NotNull
    @Min(1)
    private Integer pageSize;

    private String brand;
    private Integer priceRangeLow;
    private Integer priceRangeHigh;
    private Sort sort;
    private Category category;
}
