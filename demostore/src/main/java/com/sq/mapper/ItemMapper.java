package com.sq.mapper;

import com.sq.mapper.condition.ItemBatchSearchCondition;
import com.sq.pojo.Item;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    List<Item> selectAllItems();

    void deleteAll();

    List<Item> selectItemsByCondition(ItemBatchSearchCondition itemBatchSearchCondition);
}
