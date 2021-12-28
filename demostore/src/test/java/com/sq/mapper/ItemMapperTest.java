package com.sq.mapper;

import com.sq.pojo.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemMapperTest {
    @Autowired
    private ItemMapper itemMapper;

    @BeforeEach
    public void init() {
        Item item1 = new Item();
        item1.setId(0l);
        item1.setNum(10);
        item1.setPrice(new BigDecimal(10));
        item1.setSellPoint("123");
        item1.setStatus(0);
        Item item2 = new Item();
        item2.setId(1l);
        item2.setNum(10);
        item2.setPrice(new BigDecimal(10));
        item2.setSellPoint("123234");
        item2.setStatus(0);
        itemMapper.insert(item1);
        itemMapper.insert(item2);
    }

    @AfterEach
    public void tearDown() {
        itemMapper.deleteAll();
    }

    @Test
    @DisplayName("select all items should return all items")
    public void selectAllItems() {
        List<Item> items = itemMapper.selectAllItems();
        assertEquals(2, items.size());
    }

    @Test
    @DisplayName("insert should insert an item")
    public void insert() {
        Item item1 = new Item();
        item1.setId(3l);
        item1.setNum(10);
        item1.setPrice(new BigDecimal(10));
        item1.setSellPoint("123");
        item1.setStatus(0);
        int insert = itemMapper.insert(item1);
        assertEquals(1, insert);
    }

    @Test
    @DisplayName("deleteByPrimaryKey should delete item by id")
    public void delete() {
        List<Item> items = itemMapper.selectAllItems();
        int i = itemMapper.deleteByPrimaryKey(items.get(0).getId());
        assertEquals(1, i);
    }
}
