package com.sq.service;

import com.sq.dto.item.ItemBatchMetadata;
import com.sq.dto.item.ItemBatchPostDto;
import com.sq.dto.item.ItemDto;
import com.sq.dto.order.OrderItemDto;
import com.sq.pojo.Item;

import java.util.List;


public interface ItemService {

    ItemBatchMetadata batchGetItems(ItemBatchPostDto itemBatchPostDto);

    List<ItemDto> getItemsById(List<Long> ids);

    Item getItemById(Long id);

    void insertItem(Item item);

    void deleteItem(Long id);

    OrderItemDto checkItemAvailability(Long id, Integer quantity);
}
