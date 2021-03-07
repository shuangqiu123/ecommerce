package com.sq.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sq.dto.ItemDto;
import com.sq.item.mapper.ItemMapper;
import com.sq.item.service.ItemService;
import com.sq.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;


    // page split
    @Override
    public PageInfo selectAllItemsByPage(ItemDto itemDto) {
        PageHelper.startPage(itemDto.getPageNum(), itemDto.getPageSize());
        List<Item> items = itemMapper.selectAllItems();
        PageInfo<Item> pageInfo =new PageInfo<>(items);
        return pageInfo;
    }

    @Override
    public Item getItemById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }
}
