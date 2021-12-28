package com.sq.mapper;

import com.sq.pojo.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemMapperTest {
    @Autowired
    private ItemMapper itemMapper;

    @Test
    @DisplayName("select all items should return all items")
    @Rollback(false)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void selectAllItems() {
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
        List<Item> items = itemMapper.selectAllItems();
        Item returnedItem1 = items.get(0);
        Item returnedItem2 = items.get(1);
        assertEquals(Long.valueOf(0L), returnedItem1.getId());
        assertEquals(Long.valueOf(1L), returnedItem2.getId());
        itemMapper.deleteAll();
    }

    @Test
    @Rollback(false)
    @DisplayName("insert should insert an item")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void insert() {
        Item item1 = new Item();
        item1.setId(3l);
        item1.setNum(10);
        item1.setPrice(new BigDecimal(10));
        item1.setSellPoint("123");
        item1.setStatus(0);
        int insert = itemMapper.insert(item1);
        assertEquals(1, insert);
        itemMapper.deleteAll();
    }

    @Test
    @DisplayName("deleteByPrimaryKey should delete item by id")
    @Rollback(false)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete() {
        Item item1 = new Item();
        item1.setId(4l);
        item1.setNum(10);
        item1.setPrice(new BigDecimal(10));
        item1.setSellPoint("123");
        item1.setStatus(0);
        itemMapper.insert(item1);
        int i = itemMapper.deleteByPrimaryKey(4l);
        assertEquals(1, i);
    }
}
