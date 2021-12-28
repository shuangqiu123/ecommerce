package com.sq.service;

import com.github.pagehelper.PageInfo;
import com.sq.pojo.Item;


public interface ItemService {

    PageInfo selectAllItemsByPage(Integer pageNum, Integer pageSize);

    Item getItemById(Long id);

    void insertItem(Item item);

    void deleteItem(Long id);
}
