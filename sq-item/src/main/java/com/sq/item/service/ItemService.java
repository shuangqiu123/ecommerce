package com.sq.item.service;

import com.github.pagehelper.PageInfo;
import com.sq.dto.ItemDto;
import com.sq.pojo.Item;


public interface ItemService {

    PageInfo selectAllItemsByPage(ItemDto itemDto);

    Item getItemById(Long id);
}
