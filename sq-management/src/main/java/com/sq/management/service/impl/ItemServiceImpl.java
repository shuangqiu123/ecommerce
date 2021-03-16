package com.sq.management.service.impl;

import com.sq.management.mapper.ItemMapper;
import com.sq.management.service.ItemService;
import com.sq.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public void insertItem(Item item) {
        itemMapper.insert(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemMapper.deleteByPrimaryKey(id);
    }
}
