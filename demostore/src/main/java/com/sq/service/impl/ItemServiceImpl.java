package com.sq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sq.mapper.ItemMapper;
import com.sq.service.ItemService;
import com.sq.pojo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    // page split
    @Override
    public PageInfo selectAllItemsByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemMapper.selectAllItems();
        PageInfo<Item> pageInfo =new PageInfo<>(items);
        return pageInfo;
    }

    @Override
    public Item getItemById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertItem(Item item) {
        itemMapper.insert(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemMapper.deleteByPrimaryKey(id);
    }
}
