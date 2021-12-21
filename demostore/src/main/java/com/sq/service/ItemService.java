package com.sq.service;

import com.github.pagehelper.PageInfo;
import com.sq.dto.ItemDto;
import com.sq.pojo.Item;


public interface ItemService {

    PageInfo selectAllItemsByPage(ItemDto itemDto);

    Item getItemById(Long id);

    void insertItem(Item item);

    void deleteItem(Long id);
}
