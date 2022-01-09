package com.sq.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemBatchMetadata {
    private List<ItemBatchDisplay> itemBatchDisplayList;
    private int total;
}
