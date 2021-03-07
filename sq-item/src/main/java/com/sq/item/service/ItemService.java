package com.sq.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sq.dto.ItemDto;
import com.sq.pojo.Item;

import java.util.List;

public interface ItemService {

    public PageInfo selectAllItemsByPage(ItemDto itemDto);

    public Item getItemById(Long id);
}
