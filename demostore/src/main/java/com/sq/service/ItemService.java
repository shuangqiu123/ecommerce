package com.sq.service;

import com.sq.dto.item.ItemBatchMetadata;
import com.sq.dto.item.ItemBatchPostDto;
import com.sq.pojo.Item;


public interface ItemService {

    ItemBatchMetadata batchGetItems(ItemBatchPostDto itemBatchPostDto);

    Item getItemById(Long id);

    void insertItem(Item item);

    void deleteItem(Long id);
}
