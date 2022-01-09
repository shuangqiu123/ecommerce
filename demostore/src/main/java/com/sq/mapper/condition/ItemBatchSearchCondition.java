package com.sq.mapper.condition;

import com.sq.constant.Sort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemBatchSearchCondition {
    private String brand;
    private Integer priceRangeLow;
    private Integer priceRangeHigh;
    private Integer sort;
}
