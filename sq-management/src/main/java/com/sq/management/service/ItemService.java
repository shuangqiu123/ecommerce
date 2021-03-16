package com.sq.management.service;

import com.sq.pojo.Item;

public interface ItemService {
    void insertItem(Item item);

    void deleteItem(Long id);
}
